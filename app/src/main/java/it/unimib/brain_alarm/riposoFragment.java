package it.unimib.brain_alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import it.unimib.worldnews.adapter.NewsRecyclerViewAdapter;



public class riposoFragment extends Fragment {


    public riposoFragment() {
        // Required empty public constructor
    }


    public static riposoFragment newInstance(String param1, String param2) {
        riposoFragment fragment = new riposoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



        RecyclerView recyclerViewCountryNews = view.findViewById(R.id.recyclerview_country_news);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<News> newsList = getNewsListWithWithGSon();

        NewsRecyclerViewAdapter newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(newsList,
                new NewsRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onNewsItemClick(News news) {
                        Snackbar.make(view, news.getTitle(), Snackbar.LENGTH_SHORT).show();
                    }

                    //clicco sulla cella esce snackbar titolo, clicco su cancella esce dimensione lista
                    @Override
                    public void onDeleteButtonPressed(int position) {
                        Snackbar.make(view, getString(R.string.list_size_message) + newsList.size(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
        recyclerViewCountryNews.setLayoutManager(layoutManager);
        recyclerViewCountryNews.setAdapter(newsRecyclerViewAdapter);



    }

}