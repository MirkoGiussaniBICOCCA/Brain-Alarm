package it.unimib.brain_alarm;

import static android.content.ContentValues.TAG;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_SVEGLIA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;

import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class AggiungiFragment extends Fragment {

    //String settimana;

    private TimePicker timeP;
    private TextInputLayout nomeSveglia;

    private CheckBox checkboxL;
    private CheckBox checkboxMa;
    private CheckBox checkboxMe;
    private CheckBox checkboxG;
    private CheckBox checkboxV;
    private CheckBox checkboxS;

    private CheckBox checkboxD;

    private static String suono, vibrazione;

    private Switch switchPosticipa;

    public AggiungiFragment() {
        // Required empty public constructor
    }

    private boolean isSfida = false;
    private boolean isClassica = false;

    public static AggiungiFragment newInstance(String param1, String param2) {
        AggiungiFragment fragment = new AggiungiFragment();
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



        return inflater.inflate(R.layout.fragment_aggiungi, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonClassica = view.findViewById(R.id.classica);
        buttonClassica.setOnClickListener(v -> {
            if (!isClassica) {
                isClassica = true;
                isSfida = false;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.grigio)); }
            else {
                isClassica = false;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.azzurro));
            }
        } );

        final Button buttonSfida = view.findViewById(R.id.sfida);
        buttonSfida.setOnClickListener(v -> {
            if (!isSfida) {
                isSfida = true;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.grigio));
                Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_sfidaFragment); }
            else {
                //TODO quando imposto sfida e la salvo devo mantenere isSfida = true
                isSfida = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));
            }
        } );

        timeP = view.findViewById(R.id.timePicker);

        nomeSveglia = view.findViewById(R.id.inputLayoutNomeSveglia);

        checkboxL = view.findViewById(R.id.lunedi);
        checkboxMa = view.findViewById(R.id.martedi);
        checkboxMe = view.findViewById(R.id.mercoledi);
        checkboxG = view.findViewById(R.id.giovedi);
        checkboxV = view.findViewById(R.id.venerdi);
        checkboxS = view.findViewById(R.id.sabato);
        checkboxD = view.findViewById(R.id.domenica);

        switchPosticipa = view.findViewById(R.id.posticipa);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        Set<String> svegliaArray = sharedPref.getStringSet("sveglia", null);



        final Button buttonConferma = view.findViewById(R.id.conferma);
        buttonConferma.setOnClickListener(v -> {
            if (isSfida || isClassica) {
                Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
                saveInformation();
                saveArrayInformation();
            }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxModalità), Snackbar.LENGTH_LONG).show();

        } );

        final Button buttonAnnulla = view.findViewById(R.id.annulla);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
        } );

        final Button buttoneSuono = view.findViewById(R.id.bottoneSuono);
        buttoneSuono.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_suoniFragment);

        });




    }


    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        String orario = new String();
        orario = String.valueOf(timeP.getHour()) + ":" + String.valueOf(timeP.getMinute()) ;

        String etichetta = new String();
        etichetta = nomeSveglia.getEditText().getText().toString();

        String settimana = new String();

        settimana = "";

        if (checkboxL.isChecked()) {settimana += "1";}
        else settimana += "0";

        if (checkboxMa.isChecked()) {settimana += "2";}
        else settimana += "0";

        if (checkboxMe.isChecked()) {settimana += "3";}
        else settimana += "0";

        if (checkboxG.isChecked()) {settimana += "4";}
        else settimana += "0";

        if (checkboxV.isChecked()) {settimana += "5";}
        else settimana += "0";

        if (checkboxS.isChecked()) {settimana += "6";}
        else settimana += "0";

        if (checkboxD.isChecked()) {settimana += "7";}
        else settimana += "0";

        Log.d(TAG, "settimana = " + settimana);


        String posticipa = new String();
        posticipa = String.valueOf(switchPosticipa.isChecked());

        String modalita = new String();
        if (isClassica)
            modalita = "tc";
        else if (isSfida)
            modalita = "ts";



        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("orario", orario);
        editor.putString("etichetta", etichetta);
        editor.putString("settimana", settimana);
        editor.putString("posticipa", posticipa);
        editor.putString("modalita", modalita);
        editor.apply();

    }

    private void saveArrayInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        Set<String> sveglia = new HashSet<>();

        sveglia.add(sharedPref.getString("orario", null));
        sveglia.add(sharedPref.getString("etichetta", null));
        sveglia.add(sharedPref.getString("settimana", null));
        sveglia.add(sharedPref.getString("suono", null));
        sveglia.add(sharedPref.getString("vibrazione", null));
        sveglia.add(sharedPref.getString("posticipa", null));
        sveglia.add(sharedPref.getString("modalita", null));
        sveglia.add("attiva");  //TODO dentro home fragment si deve poter attivare e disattivare la sveglia

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("sveglia", sveglia);
        editor.apply();

    }


}
