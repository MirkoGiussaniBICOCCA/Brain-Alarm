package it.unimib.brain_alarm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextSwitcher;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.AggiungiActivity;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.adapter.SveglieAdapter;


public class HomeFragment extends Fragment {

    private static final String TAG = AggiungiActivity.class.getSimpleName();

    private TextClock txtCl;
    private ProgressBar progressBar;

    private SveglieAdapter sveglieAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

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

        txtCl = view.findViewById(R.id.txtClock);
        txtCl.setFormat12Hour("hh:mm:ss a");

        final Button buttonAggiungi = view.findViewById(R.id.bottoneAggiungiSveglia);

        buttonAggiungi.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_aggiungiActivity);
        });

        final Button buttonEliminaSveglie = view.findViewById(R.id.cancellaSveglie);

        buttonEliminaSveglie.setOnClickListener(v -> {
            Log.d(TAG, "click remove tutte" );
            removeAllSavedSets();
        });

        /*
        final Button buttonEliminaSingolaSveglia = view.findViewById(R.id.cancellaSingolaSveglia);

        buttonEliminaSveglie.setOnClickListener(v -> {
            Log.d(TAG, "click remove una" );
            removeSavedSet(key);
        });
        */


        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_sveglie);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);



        // Utilizzare un layout manager per la RecyclerView
        recyclerView.setLayoutManager(layoutManager);



        // Caricare i dati da SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("sveglia_keys", new HashSet<>());
        List<String> dataList = new ArrayList<>(set);

        // Specificare l'adapter
        recyclerView.setAdapter(sveglieAdapter);






    }


    private void removeAllSavedSets() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d(TAG, "remove " );
        // Recupera l'elenco delle chiavi e rimuove ogni set associato
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());

        for (String key : keySet) {
            Log.d(TAG, "key " + key);
            editor.remove(key);
        }

        Log.d(TAG, "dopo for");
        // Rimuove l'elenco delle chiavi
        editor.remove("sveglia_keys");

        editor.apply();
    }


    private void removeSavedSet(String key) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Rimuove il set associato alla chiave
        editor.remove(key);

        // Aggiorna l'elenco delle chiavi
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
        keySet.remove(key);
        editor.putStringSet("sveglia_keys", keySet);

        editor.apply();
    }


}