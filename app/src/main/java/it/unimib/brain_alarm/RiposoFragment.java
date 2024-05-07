package it.unimib.brain_alarm;

import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
import static it.unimib.brain_alarm.util.Constants.UNITED_KINGDOM;
import static it.unimib.brain_alarm.util.Constants.UNITED_STATES;
import static it.unimib.brain_alarm.util.Constants.LAST_UPDATE;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;


import androidx.annotation.NonNull;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.brain_alarm.News.Result;
import it.unimib.brain_alarm.Repository.INewsRepositoryWithLiveData;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.util.ErrorMessagesUtil;
import it.unimib.brain_alarm.util.ServiceLocator;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class RiposoFragment extends Fragment {

    private static final String TAG = RiposoFragment.class.getSimpleName();

    private List<News> newsList;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ProgressBar progressBar;
    private NewsViewModel newsViewModel;


    public RiposoFragment() {
        // Required empty public constructor
    }
    public static RiposoFragment newInstance() {
        return new RiposoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "qui debug mode: " + requireActivity().getResources().getBoolean(R.bool.debug_mode));

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        //qui ottengo istanza del repository con serviceLocator
        //con serviceLocator leggo file properties e vedo se sono in debug mode o no
        INewsRepositoryWithLiveData newsRepositoryWithLiveData =
                ServiceLocator.getInstance().getNewsRepository(
                        requireActivity().getApplication(),
                        requireActivity().getApplication().getResources().getBoolean(R.bool.debug_mode)
                );

        //creazione viewModel con parametri personalizzati (passo repository)
        //viewModelProvider per instanziare viewModel
        //instanzio viewModel a cui passo activity e gli dico di instanziare view model con repository
        newsViewModel = new ViewModelProvider(
                requireActivity(),
                new NewsViewModelFactory(newsRepositoryWithLiveData)).get(NewsViewModel.class);

        newsList = new ArrayList<>();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riposo, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView recyclerViewCountryNews = view.findViewById(R.id.recyclerview_news);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);


        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(newsList,
                requireActivity().getApplication(),
                new NewsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onNewsItemClick(News news) {
                        Snackbar.make(view, news.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFavoriteButtonPressed(int position) {
                        newsList.get(position).setFavorite(!newsList.get(position).isFavorite());
                        newsViewModel.updateNews(newsList.get(position));
                    }
                });

        recyclerViewCountryNews.setLayoutManager(layoutManager);
        recyclerViewCountryNews.setAdapter(newsRecyclerViewAdapter);

        String country = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST);
        Log.d(TAG, "country :" + country) ;

        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }
        //vado a recuperare country da shared preference e chiamo metodo fetchNews
        progressBar.setVisibility(View.VISIBLE);

        //mi metto in ascolto dei cambiamento che avvengono a livedata che ottengo dal getNews del viewModel
        newsViewModel.getNews(country, Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(),
                //uso inizializzazione anonima
                //livedata è associato a result quindi controllo se ho result success o error
                result -> {
                    //o aggiorno la lista o mostro messaggio di errore
                    if (result.isSuccess()) {
                        //logica per aggiornare recycler view
                        int initialSize = this.newsList.size();
                        this.newsList.clear();
                        this.newsList.addAll(((Result.Success) result).getData().getNewsList());
                        newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize, this.newsList.size());
                        progressBar.setVisibility(View.GONE);
                    } else {
                        //per mostrare snackbar dell'errore
                        ErrorMessagesUtil errorMessagesUtil =
                                new ErrorMessagesUtil(requireActivity().getApplication());
                        Snackbar.make(view, errorMessagesUtil.
                                        getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });



    }




}