package it.unimib.brain_alarm;


import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.brain_alarm.util.Constants.UNITED_KINGDOM;
import static it.unimib.brain_alarm.util.Constants.UNITED_STATES;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class ImpostazioniFragment extends Fragment {

    private static final String TAG = ImpostazioniFragment.class.getSimpleName();

    private LinearLayout layoutModifica;
    TextView nomeImpostazioni;
    private static String nome;

    private TextInputLayout inputLayoutNome;
    TextView statoImpostazioni;
    private static String stato;
    private Spinner spinnerCountries;



    public ImpostazioniFragment() {
        // Required empty public constructor
    }


    public static ImpostazioniFragment newInstance(String param1, String param2) {
        ImpostazioniFragment fragment = new ImpostazioniFragment();
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
        return inflater.inflate(R.layout.fragment_impostazioni, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonCambia = view.findViewById(R.id.buttonImpostazioni);
        final Button buttonSalva = view.findViewById(R.id.salvaImpostazioni);
        spinnerCountries = view.findViewById(R.id.spinner_countries);
        layoutModifica = view.findViewById(R.id.layoutModifica);
        inputLayoutNome = view.findViewById(R.id.inputLayoutNome);

        buttonCambia.setOnClickListener(v -> {
            layoutModifica.setVisibility(view.VISIBLE);
        });


        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        nome = sharedPref.getString("nome", null);
        stato = sharedPref.getString("stato", null);

        nomeImpostazioni = view.findViewById(R.id.nomeImpostazioni);
        statoImpostazioni = view.findViewById(R.id.statoImpostazioni);

        if(nome != null && stato!=null) {
            nomeImpostazioni.setText("Nome: " + nome);
            statoImpostazioni.setText("Stato: " + stato);
        }

        buttonSalva.setOnClickListener(v -> {

            nome = inputLayoutNome.getEditText().getText().toString();
            stato = spinnerCountries.getSelectedItem().toString();

            if (nome!=null && stato!=null) {
                saveInformation();

                //visualizzare nuovo nome
                nomeImpostazioni.setText("Nome: " + sharedPref.getString("nome", null));

                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
                statoImpostazioni.setText("Stato: " + sharedPreferencesUtil.readStringData(
                        SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST));

                layoutModifica.setVisibility(view.GONE);
            } else
                nomeImpostazioni.setText("null");
        });

        Log.d(TAG, "nome shared pref " + nome);
        Log.d(TAG, "stato shared pref " + stato);


    }

    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String countryShortName = getShortNameCountryOfInterest(stato);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
        sharedPreferencesUtil.writeStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST,
                countryShortName);

        editor.putString("nome", nome);
        editor.apply();
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

}