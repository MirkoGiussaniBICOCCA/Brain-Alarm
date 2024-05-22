package it.unimib.brain_alarm.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextClock;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.AggiungiActivity;
import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.Sveglia.Sveglie;
import it.unimib.brain_alarm.adapter.NewsRecyclerViewAdapter;
import it.unimib.brain_alarm.adapter.SveglieAdapter;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;


public class HomeFragment extends Fragment {

    private static final String PREFERENCES_NAME = "MyAppPreferences";
    public static final String SVEGLIE_KEY = "SveglieKey";
    private SharedPreferences sharedPreferences;
    private Gson gson;


    private NewsRecyclerViewAdapter newsRecyclerViewAdapter;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private ProgressBar progressBarSveglie;
    private ViewModel viewModel;

    private static final String TAG = AggiungiActivity.class.getSimpleName();

    private TextClock txtCl;
    private ProgressBar progressBar;

    private SveglieAdapter sveglieAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

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


        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_sveglie);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);


        Log.d(TAG, "prova");
        List<Sveglie> sveglieList = getSveglie();

        Log.d(TAG, sveglieList.toString());


        Context context;
        context = null;
        SveglieAdapter sveglieAdapter = new SveglieAdapter(context, sveglieList,
                new SveglieAdapter.OnItemClickListenerS() {
                    @Override
                    public void onSveglieItemClick(Sveglie sveglie) {
                        Snackbar.make(view, sveglie.getOrario(), Snackbar.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onDeleteButtonPressed(int position) {
                        Snackbar.make(view, getString(R.string.list_size_message) + sveglieList.size(),
                                Snackbar.LENGTH_SHORT).show();
                    }

                });

        // Utilizzare un layout manager per la RecyclerView
        recyclerView.setLayoutManager(layoutManager);

        // Specificare l'adapter
        recyclerView.setAdapter(sveglieAdapter);

    }

    private List<Sveglie> getSveglie() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        // Recupera l'elenco delle chiavi e rimuove ogni set associato
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());

        Log.d(TAG, "keySet " + keySet);

        List<Set<String>> setList = new ArrayList<>();
        List<Sveglie> listSveglie = new ArrayList<>();

        for (String key : keySet) {
            Log.d(TAG, "elenco key " + key);
            Set<String> sveglieSet = sharedPref.getStringSet(key, new HashSet<>());
            Log.d(TAG, "sveglieSet " + sveglieSet);
            setList.add(sveglieSet);

            Sveglie sveglia = new Sveglie(sveglieSet);
            listSveglie.add(sveglia);
            }
        Log.d(TAG, "keySet " + keySet);
        Log.d(TAG, "listSveglie " + listSveglie);

        return listSveglie;
    }

/*
    public void saveSveglie(List<Sveglie> sveglieList) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonSveglie = gson.toJson(sveglieList);
        editor.putString(SVEGLIE_KEY, jsonSveglie);
        editor.apply();
    }
*/

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