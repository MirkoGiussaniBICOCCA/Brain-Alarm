package it.unimib.brain_alarm;

import static android.content.ContentValues.TAG;
import static androidx.constraintlayout.widget.ConstraintSet.VISIBLE;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_SVEGLIA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;

import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class AggiungiFragment extends Fragment {

    private TimePicker timeP;
    private TextInputLayout nomeSveglia;

    private CheckBox checkboxL;
    private CheckBox checkboxMa;
    private CheckBox checkboxMe;
    private CheckBox checkboxG;
    private CheckBox checkboxV;
    private CheckBox checkboxS;

    private CheckBox checkboxD;

    Chip chipVibrazione;
    private boolean isVibrazione = false;

    Spinner spinnerSuoni;

    private Switch switchPosticipa;


    LinearLayout layoutSfida;
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

    private boolean isSfida = false;
    private boolean isClassica = false;

    public AggiungiFragment() {
        // Required empty public constructor
    }

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


        timeP = view.findViewById(R.id.timePicker);

        nomeSveglia = view.findViewById(R.id.inputLayoutNomeSveglia);

        checkboxL = view.findViewById(R.id.lunedi);
        checkboxMa = view.findViewById(R.id.martedi);
        checkboxMe = view.findViewById(R.id.mercoledi);
        checkboxG = view.findViewById(R.id.giovedi);
        checkboxV = view.findViewById(R.id.venerdi);
        checkboxS = view.findViewById(R.id.sabato);
        checkboxD = view.findViewById(R.id.domenica);

        spinnerSuoni = view.findViewById(R.id.spinnerSuono);

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

        switchPosticipa = view.findViewById(R.id.posticipa);

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

        layoutSfida = view.findViewById(R.id.layoutSfida);

        final Button buttonSfida = view.findViewById(R.id.sfida);
        buttonSfida.setOnClickListener(v -> {
            if (!isSfida) {
                isSfida = true;
                isClassica = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.grigio));
                layoutSfida.setVisibility(view.VISIBLE);
            }
            else {
                //TODO quando imposto sfida e la salvo devo mantenere isSfida = true
                isSfida = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));
            }
        } );


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


        final Button buttonSalvaSfide = view.findViewById(R.id.salvaSfida);
        buttonSalvaSfide.setOnClickListener(v -> {
            if (progCalcolatrice>0 || progMemory>0 || progScrivere>0 || progPassi>0) {
                isSfida = true;
                layoutSfida.setVisibility(view.GONE);
            }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxSfida), Snackbar.LENGTH_LONG).show();
        });

        final Button buttonAnnullaSfide = view.findViewById(R.id.bottoneAnnullaSfide);
        buttonAnnullaSfide.setOnClickListener(v -> {
            isSfida = false;
            layoutSfida.setVisibility(view.GONE);
            buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));
        });




        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        Set<String> svegliaArray = sharedPref.getStringSet("sveglia", null);




        final Button buttonConferma = view.findViewById(R.id.conferma);
        buttonConferma.setOnClickListener(v -> {
            if (isSfida || isClassica) {
                Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
                saveInformation();
            }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxModalità), Snackbar.LENGTH_LONG).show();

        } );

        final Button buttonAnnulla = view.findViewById(R.id.annulla);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
        } );

    }


    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        Set<String> sveglia = new HashSet<>();

        //ora
        sveglia.add( String.valueOf(timeP.getHour()) + ":" + String.valueOf(timeP.getMinute()) );

        //etichetta
        sveglia.add( nomeSveglia.getEditText().getText().toString());

        //ripetizioni
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

        sveglia.add(settimana);



        //suono, vibrazione
        sveglia.add(spinnerSuoni.getSelectedItem().toString());
        if (isVibrazione)
            sveglia.add("1");
        else
            sveglia.add("0");



        sveglia.add(String.valueOf(switchPosticipa.isChecked()));

        //sfida o classica
        if (isClassica)
            sveglia.add("tc");
        else if (isSfida)
            sveglia.add("ts");


        sveglia.add(String.valueOf(progCalcolatrice) + String.valueOf(progMemory) + String.valueOf(progScrivere) + String.valueOf(progPassi));

        sveglia.add("attiva");  //TODO dentro home fragment si deve poter attivare e disattivare la sveglia

        SharedPreferences.Editor editor = sharedPref.edit();
        String uniqueKey = "sveglia_" + System.currentTimeMillis();

        editor.putStringSet(uniqueKey, sveglia);

        // Recupera e aggiorna l'elenco delle chiavi
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
        keySet.add(uniqueKey);
        editor.putStringSet("sveglia_keys", keySet);

        editor.apply();
    }


}
