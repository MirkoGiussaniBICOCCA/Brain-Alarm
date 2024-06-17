package it.unimib.brain_alarm;

import static android.content.ContentValues.TAG;
import static it.unimib.brain_alarm.util.Constants.LAST_UPDATE;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.brain_alarm.util.Constants.TOP_HEADLINES_PAGE_SIZE_VALUE;


import androidx.annotation.NonNull;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.databinding.FragmentNewsBinding;
import it.unimib.brain_alarm.News.NewsApiResponse;
import it.unimib.brain_alarm.News.NewsResponse;
import it.unimib.brain_alarm.News.Result;
import it.unimib.brain_alarm.Repository.INewsRepositoryWithLiveData;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.databinding.FragmentNewsBinding;
import it.unimib.brain_alarm.util.ErrorMessagesUtil;
import it.unimib.brain_alarm.util.ServiceLocator;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class NewsFragment extends Fragment {

    private static final String TAG = NewsFragment.class.getSimpleName();


    ImageButton buttonStato;

    private FragmentNewsBinding fragmentNewsBinding;


    private List<News> newsList;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ProgressBar progressBar;
    private NewsViewModel newsViewModel;

    private int totalItemCount; // Total number of news
    private int lastVisibleItem; // The position of the last visible news item
    private int visibleItemCount; // Number or total visible news items

    // Based on this value, the process of loading more news is anticipated or postponed
    // Look at the if condition at line 237 to see how it is used
    private final int threshold = 1;


    public NewsFragment() {
        // Required empty public constructor
    }
    public static NewsFragment newInstance() {
        return new NewsFragment();
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
        fragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false);
        return fragmentNewsBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonStato = view.findViewById(R.id.imageButtonStato);

        //cliccando sulla bandiera va a impostazioni
        /*
        buttonStato.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_newsFragment_to_impostazioniFragment);
        });
        */

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
        if (sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST) == null)
            sharedPreferencesUtil.writeStringData(
                    SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST,
                    "it");

        switch (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST)) {
            case "it":
                buttonStato.setImageDrawable(getResources().getDrawable(R.drawable.italia));
                break;
            case "fr":
                buttonStato.setImageDrawable(getResources().getDrawable(R.drawable.francia));
                break;
            case "de":
                buttonStato.setImageDrawable(getResources().getDrawable(R.drawable.germania));
                break;
            case "gb":
                buttonStato.setImageDrawable(getResources().getDrawable(R.drawable.uk));
                break;
            case "us":
                buttonStato.setImageDrawable(getResources().getDrawable(R.drawable.usa));
                break;


        }

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


        RecyclerView recyclerViewNews = view.findViewById(R.id.recyclerview_news);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);


        newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(newsList,
                requireActivity().getApplication(),
                new NewsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onNewsItemClick(News news) {
                        NewsFragmentDirections.ActionNewsFragmentToNewsDetailFragment action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(news);
                        Navigation.findNavController(view).navigate(action);
                    }

                    @Override
                    public void onFavoriteButtonPressed(int position) {
                        newsList.get(position).setFavorite(!newsList.get(position).isFavorite());
                        newsViewModel.updateNews(newsList.get(position));
                    }
                });

        recyclerViewNews.setLayoutManager(layoutManager);
        recyclerViewNews.setAdapter(newsRecyclerViewAdapter);

        String country = sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST);
        //Log.d(TAG, "country :" + country) ;

        String lastUpdate = "0";
        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }


        //vado a recuperare country da shared preference e chiamo metodo fetchNews
        fragmentNewsBinding.progressBar.setVisibility(View.VISIBLE);

        //mi metto in ascolto dei cambiamento che avvengono a livedata che ottengo dal getNews del viewModel
        newsViewModel.getNews(country, Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(),
                //uso inizializzazione anonima
                //livedata è associato a result quindi controllo se ho result success o error
                result -> {
                    Log.d(TAG, "result : " + result);

                    //o aggiorno la lista o mostro messaggio di errore
                    if (result.isSuccess()) {

                        NewsResponse newsResponse = ((Result.NewsResponseSuccess) result).getData();
                        List<News> fetchedNews = newsResponse.getNewsList();

                        //logica per aggiornare recycler view

                        if (!newsViewModel.isLoading()) {
                            if (newsViewModel.isFirstLoading()) {
                                newsViewModel.setTotalResults(((NewsApiResponse) newsResponse).getTotalResults());
                                newsViewModel.setFirstLoading(false);
                                this.newsList.addAll(fetchedNews);
                                newsRecyclerViewAdapter.notifyItemRangeInserted(0,
                                        this.newsList.size());
                            } else {
                                // Updates related to the favorite status of the news
                                newsList.clear();
                                newsList.addAll(fetchedNews);
                                newsRecyclerViewAdapter.notifyItemChanged(0, fetchedNews.size());
                            }
                            fragmentNewsBinding.progressBar.setVisibility(View.GONE);
                        } else {
                            newsViewModel.setLoading(false);
                            newsViewModel.setCurrentResults(newsList.size());

                            int initialSize = newsList.size();

                            for (int i = 0; i < newsList.size(); i++) {
                                if (newsList.get(i) == null) {
                                    newsList.remove(newsList.get(i));
                                }
                            }
                            int startIndex = (newsViewModel.getPage() * TOP_HEADLINES_PAGE_SIZE_VALUE) -
                                    TOP_HEADLINES_PAGE_SIZE_VALUE;
                            for (int i = startIndex; i < fetchedNews.size(); i++) {
                                newsList.add(fetchedNews.get(i));
                            }
                            newsRecyclerViewAdapter.notifyItemRangeInserted(initialSize, newsList.size());
                        }
                    } else {
                        //per mostrare snackbar dell'errore
                        Log.d(TAG, "else RESULT : " + result.toString());
                        Log.d(TAG, "else RESULT : " + result.isSuccess());

                        ErrorMessagesUtil errorMessagesUtil =
                                new ErrorMessagesUtil(requireActivity().getApplication());
                        Snackbar.make(view, errorMessagesUtil.
                                        getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                        fragmentNewsBinding.progressBar.setVisibility(View.GONE);
                    }
                });


        recyclerViewNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isConnected = isConnected();

                if (isConnected && totalItemCount != newsViewModel.getTotalResults()) {

                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    visibleItemCount = layoutManager.getChildCount();

                    // Condition to enable the loading of other news while the user is scrolling the list
                    if (totalItemCount == visibleItemCount ||
                            (totalItemCount <= (lastVisibleItem + threshold) &&
                                    dy > 0 &&
                                    !newsViewModel.isLoading()
                            ) &&
                                    newsViewModel.getNewsResponseLiveData().getValue() != null &&
                                    newsViewModel.getCurrentResults() != newsViewModel.getTotalResults()
                    ) {
                        MutableLiveData<Result> newsListMutableLiveData = newsViewModel.getNewsResponseLiveData();

                        if (newsListMutableLiveData.getValue() != null &&
                                newsListMutableLiveData.getValue().isSuccess()) {

                            newsViewModel.setLoading(true);
                            newsList.add(null);
                            newsRecyclerViewAdapter.notifyItemRangeInserted(newsList.size(),
                                    newsList.size() + 1);

                            int page = newsViewModel.getPage() + 1;
                            newsViewModel.setPage(page);
                            newsViewModel.fetchNews(country);
                        }
                    }
                }
            }
        });
    }

    /**
     * It checks if the device is connected to Internet.
     * See: <a href="https://developer.android.com/training/monitoring-device-state/connectivity-status-type#DetermineConnection">...</a>
     * @return true if the device is connected to Internet; false otherwise.
     */
    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsViewModel.setFirstLoading(true);
        newsViewModel.setLoading(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentNewsBinding = null;
    }

}