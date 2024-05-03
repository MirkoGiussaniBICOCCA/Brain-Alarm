package it.unimib.brain_alarm;

import android.os.Bundle;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;


public class SfidaFragment extends Fragment {

    private static final String TAG = SfidaFragment.class.getSimpleName();
    private int progCalcolatrice = 0;
    private int progMemory = 0;
    private int progScrivere = 0;
    private int progPassi = 0;


    SeekBar seekBar;
    TextView value;
    ImageView imgCalcolaltrice;
    TextView textCalc;

    SeekBar seekBarM;
    TextView valueM;
    ImageView imgMemory;
    TextView textMem;


    SeekBar seekBarS;
    TextView valueS;
    ImageView imgScrivere;
    TextView textScr;

    SeekBar seekBarP;
    TextView valueP;
    ImageView imgPassi;
    TextView textPassi;



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
        imgCalcolaltrice=view.findViewById((R.id.sfida1));
        textCalc=view.findViewById((R.id.ripCalcolatrice));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressoCalcolatrice, boolean fromUser) {
                Log.d(TAG, "N calcolatrice " + progressoCalcolatrice);
                value.setText(progressoCalcolatrice + "/" + "5");
                if (progressoCalcolatrice>0) {
                    imgCalcolaltrice.setImageDrawable(getResources().getDrawable(R.drawable.calcolatrice2));
                    textCalc.setText("x" + progressoCalcolatrice);
                    progCalcolatrice = progressoCalcolatrice;
                }
                else {
                    imgCalcolaltrice.setImageDrawable(getResources().getDrawable(R.drawable.calcolatrice));
                    textCalc.setText(" ");
                    progCalcolatrice = 0;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {   }
        });

        seekBarM=view.findViewById(R.id.ripetizioniMemory);
        valueM=view.findViewById(R.id.progressoMemory);
        imgMemory=view.findViewById((R.id.sfida2));
        textMem=view.findViewById((R.id.ripMemory));

        seekBarM.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarM, int progressoMemory, boolean fromUser) {
                Log.d(TAG, "N memory " + progressoMemory);
                valueM.setText(progressoMemory + "/" + "5");
                if (progressoMemory>0) {
                    imgMemory.setImageDrawable(getResources().getDrawable(R.drawable.memory2));
                    textMem.setText("x" + progressoMemory);
                    progMemory = progressoMemory;
                }
                else {
                        imgMemory.setImageDrawable(getResources().getDrawable(R.drawable.memory));
                        textMem.setText(" ");
                        progMemory = 0;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBarM) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarM) {   }
        });

        seekBarS=view.findViewById(R.id.ripetizioniScrivere);
        valueS=view.findViewById(R.id.progressoScrivere);
        imgScrivere=view.findViewById((R.id.sfida3));
        textScr=view.findViewById((R.id.ripScrivere));

        seekBarS.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarS, int progressoScrivere, boolean fromUser) {
                Log.d(TAG, "N scrivere " + progressoScrivere);
                valueS.setText(progressoScrivere + "/" + "5");
                if (progressoScrivere>0)   {
                    imgScrivere.setImageDrawable(getResources().getDrawable(R.drawable.scrivere2));
                    textScr.setText("x" + progressoScrivere);
                    progScrivere = progressoScrivere;
                }
                else {
                    imgScrivere.setImageDrawable(getResources().getDrawable(R.drawable.scrivere));
                    textScr.setText(" ");
                    progScrivere = 0;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBarS) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarS) {   }
        });

        seekBarP=view.findViewById(R.id.ripetizioniPassi);
        valueP=view.findViewById(R.id.progressoPassi);
        imgPassi=view.findViewById((R.id.sfida4));
        textPassi=view.findViewById((R.id.ripPassi));



        seekBarP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarP, int progressoPassi, boolean fromUser) {
                Log.d(TAG, "N passi " + progressoPassi);
                valueP.setText(progressoPassi + "/" + "5");
                if (progressoPassi>0) {
                    imgPassi.setImageDrawable(getResources().getDrawable(R.drawable.passi2));
                    textPassi.setText("x" + progressoPassi);
                    progPassi = progressoPassi;
                }
                else {
                    imgPassi.setImageDrawable(getResources().getDrawable(R.drawable.passi));
                    textPassi.setText(" ");
                    progPassi = 0;
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBarP) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarP) {   }
        });


        final Button buttonSalva = view.findViewById(R.id.SalvaSfida);
        buttonSalva.setOnClickListener(v -> {
            if (progCalcolatrice>0 || progMemory>0 || progScrivere>0 || progPassi>0) {
                Navigation.findNavController(v).navigate(R.id.nav_sfidaFragment_to_aggiungiFragment);
                Log.d(TAG, "ripetizioni " + progCalcolatrice + progMemory + progScrivere + progPassi);
                }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxSfida), Snackbar.LENGTH_LONG).show();
        });

        final Button buttonAnnulla = view.findViewById(R.id.bottoneAnnullaSfide);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_sfidaFragment_to_aggiungiFragment);
        });
    }




}



