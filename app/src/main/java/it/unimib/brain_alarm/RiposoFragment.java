package it.unimib.brain_alarm;

import static it.unimib.brain_alarm.util.Constants.LAST_UPDATE;
import static it.unimib.brain_alarm.util.Constants.NEWS_API_TEST_JSON_FILE;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unimib.brain_alarm.Repository.INewsRepository;
import it.unimib.brain_alarm.Repository.NewsMockRepository;
import it.unimib.brain_alarm.Repository.NewsRepository;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.util.JSONParseUtil;
import it.unimib.brain_alarm.util.ResponseCallback;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;


public class RiposoFragment extends Fragment implements ResponseCallback {

    private static final String TAG = RiposoFragment.class.getSimpleName();

    private List<News> newsList;
    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    private INewsRepository iNewsRepository;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ProgressBar progressBar;

    public RiposoFragment() {
        // Required empty public constructor
    }
    public static RiposoFragment newInstance() {
        return new RiposoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "debug mode: " + requireActivity().getResources().getBoolean(R.bool.debug_mode));

        if (requireActivity().getResources().getBoolean(R.bool.debug_mode)) {
            // Use NewsMockRepository to read the news from
            // newsapi-test.json file contained in assets folder
            iNewsRepository =
                    new NewsMockRepository(requireActivity().getApplication(), this,
                            INewsRepository.JsonParserType.GSON);
        } else {
            iNewsRepository =
                    new NewsRepository(requireActivity().getApplication(), this);
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
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

        //List<News> newsList = getNewsListWithWithGSon();

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


}