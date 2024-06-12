package it.unimib.brain_alarm;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import it.unimib.brain_alarm.Sveglia.Sveglie;
import it.unimib.brain_alarm.util.GetDateTime;

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
    private int progTris = 0;
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

    SeekBar seekBarT;
    TextView valueT;
    ImageView imgTris;
    TextView textTris;

    SeekBar seekBarP;
    TextView valueP;
    ImageView imgPassi;
    TextView textPassi;

    private boolean isSfida = false;
    private boolean isClassica = false;

    public AggiungiFragment() {
        // Required empty public constructor
    }

    public static AggiungiFragment newInstance() {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        final Button buttonSfida = view.findViewById(R.id.sfida);

        buttonClassica.setOnClickListener(v -> {
            if (!isClassica) {
                isClassica = true;
                isSfida = false;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.grigio));
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));}
            else {
                isClassica = false;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.azzurro));
            }
        } );

        layoutSfida = view.findViewById(R.id.layoutSfida);



        buttonSfida.setOnClickListener(v -> {
            if (!isSfida) {
                isSfida = true;
                isClassica = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.grigio));
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.azzurro));
                layoutSfida.setVisibility(view.VISIBLE);
            }
            else {
                isSfida = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));
                layoutSfida.setVisibility(view.GONE);
            }
        } );


        seekBar=view.findViewById(R.id.ripetizioniCalcolatrice);
        value=view.findViewById(R.id.progressoCalcolatrice);
        imgCalcolaltrice=view.findViewById((R.id.sfida1));
        textCalc=view.findViewById((R.id.ripCalcolatrice));

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressoCalcolatrice, boolean fromUser) {
                //Log.d(TAG, "N calcolatrice " + progressoCalcolatrice);
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

        seekBarM.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarM, int progressoMemory, boolean fromUser) {
                //Log.d(TAG, "N memory " + progressoMemory);
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

        seekBarS.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarS, int progressoScrivere, boolean fromUser) {
                //Log.d(TAG, "N scrivere " + progressoScrivere);
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

        seekBarT=view.findViewById(R.id.ripetizioniTris);
        valueT=view.findViewById(R.id.progressoTris);
        imgTris=view.findViewById((R.id.sfida4));
        textTris=view.findViewById((R.id.ripTris));

        seekBarT.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarT, int progressoTris, boolean fromUser) {
                valueT.setText(progressoTris + "/" + "5");
                if (progressoTris>0) {
                    imgTris.setImageDrawable(getResources().getDrawable(R.drawable.tris2));
                    textTris.setText("x" + progressoTris);
                    progTris = progressoTris;
                }
                else {
                    imgTris.setImageDrawable(getResources().getDrawable(R.drawable.tris));
                    textTris.setText(" ");
                    progTris = 0;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBarP) {   }
            @Override
            public void onStopTrackingTouch(SeekBar seekBarP) {   }
        });

        seekBarP=view.findViewById(R.id.ripetizioniPassi);
        valueP=view.findViewById(R.id.progressoPassi);
        imgPassi=view.findViewById((R.id.sfida5));
        textPassi=view.findViewById((R.id.ripPassi));


        seekBarP.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBarP, int progressoPassi, boolean fromUser) {
                //Log.d(TAG, "N passi " + progressoPassi);
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
            if (progCalcolatrice>0 || progMemory>0 || progScrivere>0 || progTris>0 || progPassi>0) {
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

                if (isSfida) {
                    if (progCalcolatrice>0 || progMemory>0 || progScrivere>0 || progTris>0 || progPassi>0) {
                        isSfida = true;
                        layoutSfida.setVisibility(view.GONE);
                        Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
                        saveInformation();
                    }
                    else
                        Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxSfida), Snackbar.LENGTH_LONG).show();
                }
                if (isClassica) {
                    Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
                    saveInformation();
                }

            }
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxModalità), Snackbar.LENGTH_LONG).show();

        } );

        final Button buttonAnnulla = view.findViewById(R.id.annulla);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
        } );



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        Set<String> sveglia = new HashSet<>();

        //orario
        String ora = String.valueOf(timeP.getHour());
        if (ora.length()!=2)
            ora = "0" + ora;

        String min = String.valueOf(timeP.getMinute());
        if (min.length()!=2)
            min = "0" + min;

        String orario = "o" + ora + ":" + min + ":00";
        sveglia.add(orario);

        //etichetta
        sveglia.add("e" + nomeSveglia.getEditText().getText().toString());

        //ripetizioni
        String settimana = new String();
        settimana = "r";

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

        //Log.d(TAG, "settimana = " + settimana);

        sveglia.add(settimana);

        //va a selezionare la data più vicina da salvare in shared preferences
        sveglia.add(GetDateTime.getDateRipetizioni(settimana, orario.substring(1)));

        //suono, vibrazione
        sveglia.add(spinnerSuoni.getSelectedItem().toString());
        if (isVibrazione)
            sveglia.add("v1");
        else
            sveglia.add("v0");

        sveglia.add(String.valueOf(switchPosticipa.isChecked()));

        //sfida o classica
        if (isClassica)
            sveglia.add("tc");
        else if (isSfida)
            sveglia.add("ts");


        sveglia.add("m" + String.valueOf(progCalcolatrice) + String.valueOf(progMemory) + String.valueOf(progScrivere) + String.valueOf(progTris)+  String.valueOf(progPassi));

        sveglia.add("attiva");

        SharedPreferences.Editor editor = sharedPref.edit();
        String uniqueKey = "sveglia_" + System.currentTimeMillis();
        sveglia.add(uniqueKey);

        editor.putStringSet(uniqueKey, sveglia);

        // Recupera e aggiorna l'elenco delle chiavi
        Set<String> keySet = sharedPref.getStringSet("sveglia_keys", new HashSet<>());
        keySet.add(uniqueKey);

        Set<String> sveglieAttive = sharedPref.getStringSet("sveglieAttive", new HashSet<>());
        sveglieAttive.add(uniqueKey);

        editor.putStringSet("sveglia_keys", keySet);
        editor.putStringSet("sveglieAttive", sveglieAttive);

        editor.apply();
    }

}
