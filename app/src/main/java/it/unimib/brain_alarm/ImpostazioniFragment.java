package it.unimib.brain_alarm;


import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
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
import android.widget.Spinner;
import android.widget.TextView;

public class ImpostazioniFragment extends Fragment {

    private static final String TAG = ImpostazioniFragment.class.getSimpleName();
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

        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        stato = sharedPref.getString("stato", null);

        Log.d(TAG, "stato shared pref " + stato);

        spinnerCountries = view.findViewById(R.id.spinner_countries);

        final Button buttonSalva = view.findViewById(R.id.buttonCountry);

        buttonSalva.setOnClickListener(v -> {
            stato = spinnerCountries.getSelectedItem().toString();
            Log.d(TAG, "stato salvato " + stato);
            saveInformation();

        });

    }

    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("stato", stato);
        editor.apply();
        Log.d(TAG, "saveInf " + stato);

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