package it.unimib.brain_alarm;

import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
import static it.unimib.brain_alarm.util.Constants.UNITED_KINGDOM;
import static it.unimib.brain_alarm.util.Constants.UNITED_STATES;
import static it.unimib.brain_alarm.util.Constants.LAST_UPDATE;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.Repository.INewsRepository;
import it.unimib.brain_alarm.Repository.NewsMockRepository;
import it.unimib.brain_alarm.Repository.NewsRepository;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.util.ResponseCallback;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class RiposoFragment extends Fragment implements ResponseCallback {

    private static final String TAG = RiposoFragment.class.getSimpleName();

    private static final String COUNTRY_SAVED = "country";
    private static String stato;

    private List<News> newsList;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private INewsRepository iNewsRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ProgressBar progressBar;
    private Spinner spinnerCountries;

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

        if (requireActivity().getResources().getBoolean(R.bool.debug_mode)) {
            // Use NewsMockRepository to read the news from
            // newsapi-test.json file contained in assets folder
            // ho inizializzato la repository
            iNewsRepository =
                    new NewsMockRepository(requireActivity().getApplication(), this,
                            INewsRepository.JsonParserType.GSON);
            Log.d(TAG, "dentro if: " + requireActivity().getResources().getBoolean(R.bool.debug_mode));

        } else {
            iNewsRepository =
                    new NewsRepository(requireActivity().getApplication(), this);
            Log.d(TAG, "dentro else: " + requireActivity().getResources().getBoolean(R.bool.debug_mode));
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        newsList = new ArrayList<>();
        Log.d(TAG, "dopo" );

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riposo, container, false);
    }

    private boolean isCountryOfInterestSelected() {
        spinnerCountries = getActivity().findViewById(R.id.spinner_countries);
        if (spinnerCountries.getSelectedItem() != null) {
            return true;
        }

        else
            return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonSalva = view.findViewById(R.id.buttonCountry);
        buttonSalva.setOnClickListener(v -> {
            if (isCountryOfInterestSelected()) {
                Log.d(TAG, "One country of interest and at least one topic has been chosen");
                saveInformation();
                }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content),getString(R.string.stato), Snackbar.LENGTH_LONG).show();
        });

        if (savedInstanceState != null) {
            stato = savedInstanceState.getString(COUNTRY_SAVED);
            Log.d(TAG, "stringa salvata: " + stato);
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
                        iNewsRepository.updateNews(newsList.get(position));
                    }
                });
        recyclerViewCountryNews.setLayoutManager(layoutManager);
        recyclerViewCountryNews.setAdapter(newsRecyclerViewAdapter);

        String lastUpdate = "0";

        if (sharedPreferencesUtil.readStringData(
                SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtil.readStringData(
                    SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE);
        }
        //vado a recuperare country da shared preference e chiamo metodo fetchNews
        progressBar.setVisibility(View.VISIBLE);
        iNewsRepository.fetchNews(sharedPreferencesUtil.readStringData(
                        SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST), 0,
                Long.parseLong(lastUpdate));

    }

    @Override
    public void onSuccess(List<News> newsList, long lastUpdate) {
        if (newsList != null) {
            this.newsList.clear();
            this.newsList.addAll(newsList);
            sharedPreferencesUtil.writeStringData(SHARED_PREFERENCES_FILE_NAME, LAST_UPDATE,
                    String.valueOf(lastUpdate));
        }

        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsRecyclerViewAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onNewsFavoriteStatusChanged(@NonNull News news) {
        if (news.isFavorite()) {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.news_added_to_favorite_list_message),
                    Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    getString(R.string.news_removed_from_favorite_list_message),
                    Snackbar.LENGTH_LONG).show();
        }
    }


    private String getShortNameCountryOfInterest(String userVisibleCountryOfInterest) {
        if (userVisibleCountryOfInterest.equals(getString(R.string.francia))) {
            return FRANCE;
        } else if (userVisibleCountryOfInterest.equals(getString(R.string.germania))) {
            return GERMANY;
        } else if (userVisibleCountryOfInterest.equals(getString(R.string.italia))) {
            return ITALY;
        } else if (userVisibleCountryOfInterest.equals(getResources().getString(R.string.uk))) {
            return UNITED_KINGDOM;
        } else if (userVisibleCountryOfInterest.equals(getResources().getString(R.string.us))) {
            return UNITED_STATES;
        }
        return null;
    }




    private void saveInformation() {

        String country = spinnerCountries.getSelectedItem().toString();
        String countryShortName = getShortNameCountryOfInterest(country);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
        sharedPreferencesUtil.writeStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST, countryShortName);
    }


}