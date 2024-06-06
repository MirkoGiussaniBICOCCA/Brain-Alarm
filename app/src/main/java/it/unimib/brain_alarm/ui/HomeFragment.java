package it.unimib.brain_alarm.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.unimib.brain_alarm.AggiungiActivity;
import it.unimib.brain_alarm.NewsFragmentDirections;
import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.Sveglia.Sveglie;
import it.unimib.brain_alarm.adapter.SveglieAdapter;

import java.time.Duration;
import java.time.LocalDateTime;

public class HomeFragment extends Fragment {

    public static final String SVEGLIE_KEY = "SveglieKey";

    private static final String TAG = HomeFragment.class.getSimpleName();

    private Handler handler = new Handler();
    private Runnable runnable;

    TextView textCountDown;
    LinearLayout layoutCountDown;

    TextView textCountDownOra;
    TextView textCountDownMin;
    TextView textCountDownSec;


    LinearLayout layoutConfermaEliminazione;


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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String countDown = getCountDown(view);
        textCountDown = view.findViewById(R.id.textCountDown);
        layoutCountDown = view.findViewById(R.id.layoutCountdown);
        textCountDownOra = view.findViewById(R.id.textCountDownOra);
        textCountDownMin = view.findViewById(R.id.textCountDownMin);
        textCountDownSec = view.findViewById(R.id.textCountDownSec);




        runnable = new Runnable() {
            @Override
            public void run() {
                setText(getCountDown(view));
                handler.postDelayed(this, 1000); // Chiamata ogni 1000 millisecondi (1 secondo)
            }
        };

        aggiornaRecyclerView(view, false, "");

        final Button buttonAggiungi = view.findViewById(R.id.bottoneAggiungiSveglia);

        buttonAggiungi.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_aggiungiActivity);
        });


        layoutConfermaEliminazione = view.findViewById(R.id.layoutConfermaEliminazione);

        final Button buttonEliminaSveglie = view.findViewById(R.id.cancellaSveglie);

        buttonEliminaSveglie.setOnClickListener(v -> {

                    layoutConfermaEliminazione.setVisibility(view.VISIBLE);
        });

        final Button buttonSiEliminazione = view.findViewById(R.id.buttonSiEliminazione);

        buttonSiEliminazione.setOnClickListener(v -> {

            layoutConfermaEliminazione.setVisibility(view.GONE);

            //Log.d(TAG, "click remove tutte");
            removeAllSveglie();

            aggiornaRecyclerView(view, false, "");
        });

        final Button buttonNoEliminazione = view.findViewById(R.id.buttonNoEliminazione);

        buttonNoEliminazione.setOnClickListener(v -> {

            layoutConfermaEliminazione.setVisibility(view.GONE);
        });




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


    }

    private void aggiornaRecyclerView (View view, Boolean disattiva, String keyDaDisattivare) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_sveglie);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL, false);

        List<Sveglie> sveglieList = getSveglie();

        SveglieAdapter sveglieAdapter = new SveglieAdapter(getContext(), requireActivity().getApplication(), sveglieList, disattiva, keyDaDisattivare,
                new SveglieAdapter.OnItemClickListenerS() {
                    @Override
                    public void onSveglieItemClick(Sveglie sveglie) {
                        Snackbar.make(view, sveglie.getOrario(), Snackbar.LENGTH_SHORT).show();

                        HomeFragmentDirections.ActionHomeFragmentToModificaFragment action = HomeFragmentDirections.actionHomeFragmentToModificaFragment(sveglie);
                        Navigation.findNavController(view).navigate(action);

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



        List<Set<String>> setList = new ArrayList<>();
        List<Sveglie> listSveglie = new ArrayList<>();

        for (String key : keySet) {

            Set<String> sveglieSet = sharedPref.getStringSet(key, new HashSet<>());

            setList.add(sveglieSet);

            Sveglie sveglia = new Sveglie(sveglieSet);
            listSveglie.add(sveglia);
            }

        return listSveglie;
    }





    private void removeAllSveglie() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d(TAG, "remove " );
        // Recupera l'elenco delle chiavi e rimuove ogni set associato
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
        Set<String> sveglieAttive = sharedPref.getStringSet("sveglieAttive", new HashSet<>());



        for (String key : keySet) {
            //Log.d(TAG, "key " + key);
            editor.remove(key);
        }

        for (String attiva : sveglieAttive) {
            //Log.d(TAG, "attiva " + attiva);
            editor.remove(attiva);
        }

        //Log.d(TAG, "dopo for");
        // Rimuove l'elenco delle chiavi
        editor.remove("sveglia_keys");
        editor.remove("sveglieAttive");

        editor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setText (String countDown) {
        Log.d(TAG, "setText" + countDown.toString());
        if (countDown.toString().equals("")) {
            textCountDown.setText("Nessuna sveglia attiva");
            layoutCountDown.setVisibility(getView().GONE);
        }
        else{
            layoutCountDown.setVisibility(getView().VISIBLE);
            if (countDown.charAt(0) == '0') {
                textCountDown.setText("giorno 0");
                //textCountDown.setVisibility(getView().GONE);
            }
            else {
                textCountDown.setText(countDown.charAt(0) + " giorni e ");
            }
            textCountDownOra.setText(countDown.toString().substring(1, 5) );
            textCountDownMin.setText(countDown.toString().substring(5, 8) );
            textCountDownSec.setText(countDown.toString().substring(8,10));

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable); // Avvia il runnable quando il fragment è visibile
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getCountDown(View view) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        Set<String> attiveSet = sharedPref.getStringSet("sveglieAttive", new HashSet<>());


        String dataOrarioTemp = ""; //dataOrarioTemp è la data e l'orario della sveglia che sto controllando
        String countDown = "";
        int secondiMancanti = -1;


        for (String attive : attiveSet) {
            //Log.d(TAG, "elenco attive " + attive); //attive è la chiave della sveglia attiva

            Set<String> sveglieSetAttive = sharedPref.getStringSet(attive, new HashSet<>());
            //Log.d(TAG, "elenco sveglieAttive " + sveglieSetAttive); //elenco stringhe della sveglia attiva


            String data = "";
            String orario = "";

            //per ogni sveglia attiva prendo il suo oraeio e data
            for (String attiva : sveglieSetAttive) {
                //Log.d(TAG, "dati attiva " + attiva.toString());

                if (!attiva.toString().isEmpty()) {
                    if ((attiva.toString()).charAt(0) == 'd')
                        data += attiva.toString().substring(1) + " ";

                    if ((attiva.toString()).charAt(0) == 'o')
                        orario += attiva.toString().substring(1);
                }

            }

            dataOrarioTemp = data + orario;
            Log.d(TAG, "data e orario Attiva " + dataOrarioTemp); //data e orario della sveglia che sto controllando


            //mancanoDHM contiene giorno ora e minuti che mancano
            String mancanoDHM = calculateTimeDifference(dataOrarioTemp);
            //Log.d(TAG, "mancanoDHM " + mancanoDHM);

            //imposto da valori di default a valori della prima sveglia
            if (secondiMancanti == -1 || countDown.equals("")) {
                secondiMancanti = getSecondiMancanti(mancanoDHM);
                //Log.d(TAG, "secondiMancanti " + secondiMancanti);
                countDown = mancanoDHM;
                //Log.d(TAG, "countDown " + countDown);
            }
            //cambio valori solo se la sveglia che sto vedendo suona prima di quella già fissata
            else {
                if (secondiMancanti > getSecondiMancanti(mancanoDHM)) {
                    secondiMancanti = getSecondiMancanti(mancanoDHM);
                    countDown = mancanoDHM; }
            }


            if (getSecondiMancanti(countDown) <= 0) {

                //passo la chiave a svegliaFragment
                HomeFragmentDirections.ActionHomeFragmentToSvegliaFragment action = HomeFragmentDirections.actionHomeFragmentToSvegliaFragment(attive);
                Navigation.findNavController(view).navigate(action);

                aggiornaRecyclerView(view, true, attive);
            }

        }

        return countDown;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String calculateTimeDifference(String futureTime) {
        // Formattatore per l'orario di input
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parso l'orario futuro
        LocalDateTime futureDateTime = LocalDateTime.parse(futureTime, formatter);

        // Ottengo data e orario attuale
        LocalDateTime now = LocalDateTime.now();

        // Calcolo la durata tra l'orario attuale e l'orario futuro
        Duration duration = Duration.between(now, futureDateTime);

        // Ottengo giorni, ore, minuti e secondi dalla durata
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        DecimalFormat df = new DecimalFormat("00");
        String stringHours = df.format(hours);
        String stringdMinutes = df.format(minutes);
        String stringSeconds = df.format(seconds);

        // Formatto il risultato
        return days + " " + stringHours + ":" + stringdMinutes + ":" + stringSeconds;
    }

    public static int getSecondiMancanti(String mancanoDHM) {
        // Divide la stringa in due parti: giorni e il resto (ore, minuti, secondi)
        String[] parts = mancanoDHM.split(" ");
        int days = Integer.parseInt(parts[0]) * 24 * 60 * 60;

        // Divide la parte del tempo (ore, minuti, secondi)
        String[] timeParts = parts[1].split(":");
        int hours = Integer.parseInt(timeParts[0]) * 60 * 60;
        int minutes = Integer.parseInt(timeParts[1]) * 60;
        int seconds = Integer.parseInt(timeParts[2]);

        return days+hours+minutes+seconds;
    }





}