package it.unimib.brain_alarm;

import android.content.Intent;
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

import com.google.android.material.chip.Chip;


public class SuoniFragment extends Fragment {

    Chip chipVibrazione;
    private boolean isVibrazione = false;

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

        final Button buttonSalva = view.findViewById(R.id.salvaSuono);
        buttonSalva.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_suoniFragment_to_aggiungiFragment);
        });


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


        final Button buttonMaps = view.findViewById(R.id.maps);
        buttonMaps.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } );
    }



}