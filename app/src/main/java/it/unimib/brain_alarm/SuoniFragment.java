package it.unimib.brain_alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.material.chip.Chip;

import java.util.HashSet;
import java.util.Set;


public class SuoniFragment extends Fragment {

    Chip chipVibrazione;
    private boolean isVibrazione = false;

    private static String suono, vibrazione;

    Spinner spinnerSuoni;

    public SuoniFragment() {
        // Required empty public constructor
    }

    public static SuoniFragment newInstance(String param1, String param2) {
        SuoniFragment fragment = new SuoniFragment();
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
        return inflater.inflate(R.layout.fragment_suoni, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        chipVibrazione = view.findViewById(R.id.chipVibr);

        chipVibrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVibrazione) {
                    isVibrazione = true;
                    chipVibrazione.setChecked(true);
                    chipVibrazione.setChipIcon(getResources().getDrawable(R.drawable.check));
                    chipVibrazione.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.fucsia))); }
                else {
                    isVibrazione = false;
                    chipVibrazione.setChecked(true);
                    chipVibrazione.setChipIcon(getResources().getDrawable(R.drawable.close));
                    chipVibrazione.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.sfondoTras))); }
            }
        });


        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        suono = sharedPref.getString("suono", null);
        vibrazione = sharedPref.getString("vibrazione", null);

        spinnerSuoni = view.findViewById(R.id.spinnerSuono);

        final Button buttonSalva = view.findViewById(R.id.salvaSuono);
        buttonSalva.setOnClickListener(v -> {

            suono = spinnerSuoni.getSelectedItem().toString();
            if (isVibrazione)
                vibrazione = "1";
            else
                vibrazione = "0";
            saveInformation();

            Navigation.findNavController(v).navigate(R.id.nav_suoniFragment_to_aggiungiFragment);
        });


        final Button buttonMaps = view.findViewById(R.id.maps);
        buttonMaps.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } );
    }

    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("suono", suono);
        editor.putString("vibrazione", vibrazione);
        editor.apply();
    }


}