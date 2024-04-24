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
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SfidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SfidaFragment extends Fragment {


    SeekBar seekBar;
    TextView value;

    SeekBar seekBarM;
    TextView valueM;

    SeekBar seekBarS;
    TextView valueS;

    SeekBar seekBarP;
    TextView valueP;



    public SfidaFragment() {
        // Required empty public constructor
    }

    public static SfidaFragment newInstance(String param1, String param2) {
        SfidaFragment fragment = new SfidaFragment();
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
        return inflater.inflate(R.layout.fragment_sfida, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBar=view.findViewById(R.id.ripetizioniCalcolatrice);
        value=view.findViewById(R.id.progressoCalcolatrice);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressoCalcolatrice, boolean fromUser) {
                value.setText(progressoCalcolatrice + "/" + "5");}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {   }
        });

        seekBarM=view.findViewById(R.id.ripetizioniMemory);
        valueM=view.findViewById(R.id.progressoMemory);

        seekBarM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarM, int progressoMemory, boolean fromUser) {
                valueM.setText(progressoMemory + "/" + "5");}
            @Override
            public void onStartTrackingTouch(SeekBar seekBarM) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarM) {   }
        });

        seekBarS=view.findViewById(R.id.ripetizioniScrivere);
        valueS=view.findViewById(R.id.progressoScrivere);

        seekBarS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarS, int progressoScrivere, boolean fromUser) {
                valueS.setText(progressoScrivere + "/" + "5");}
            @Override
            public void onStartTrackingTouch(SeekBar seekBarS) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarS) {   }
        });

        seekBarP=view.findViewById(R.id.ripetizioniPassi);
        valueP=view.findViewById(R.id.progressoPassi);

        seekBarP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarP, int progressoPassi, boolean fromUser) {
                valueP.setText(progressoPassi + "/" + "5");}
            @Override
            public void onStartTrackingTouch(SeekBar seekBarP) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarP) {   }
        });


        final Button buttonSalva = view.findViewById(R.id.SalvaSfida);
        buttonSalva.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_sfidaFragment_to_aggiungiFragment);

        });
    }




}



