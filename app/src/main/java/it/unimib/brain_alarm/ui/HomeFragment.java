package it.unimib.brain_alarm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import it.unimib.brain_alarm.AggiungiActivity;
import it.unimib.brain_alarm.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = AggiungiActivity.class.getSimpleName();




    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonAggiungi = view.findViewById(R.id.bottoneAggiungiSveglia);

        buttonAggiungi.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_aggiungiActivity);
        });



        /*
        RecyclerView recyclerViewCountryNews = view.findViewById(R.id.recyclerview_country_news);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<News> newsList = getNewsListWithWithGSon();

        it.unimib.worldnews.adapter.NewsRecyclerViewAdapter newsRecyclerViewAdapter = new it.unimib.worldnews.adapter.NewsRecyclerViewAdapter(newsList,
                new it.unimib.worldnews.adapter.NewsRecyclerViewAdapter.OnItemClickListener() {
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

        */

    }





}