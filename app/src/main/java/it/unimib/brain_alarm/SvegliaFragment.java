package it.unimib.brain_alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class SvegliaFragment extends Fragment {

    private static final String TAG = SvegliaFragment.class.getSimpleName();


    private MediaPlayer mediaPlayer;

    LinearLayout barraSfide;

    int ripCalcolatrice = 0;
    int ripMemory = 0;
    Integer ripScrivere = 0;
    Integer ripPassi = 0;

    ImageView imgCalcolaltrice;
    TextView textCalc;

    ImageView imgMemory;
    TextView textMem;

    ImageView imgScrivere;
    TextView textScr;

    ImageView imgPassi;
    TextView textPassi;



    public SvegliaFragment() {
        // Required empty public constructor
    }


    public static SvegliaFragment newInstance() {
        SvegliaFragment fragment = new SvegliaFragment();
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
        return inflater.inflate(R.layout.fragment_sveglia, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.suono2);

        startAlarm();


        String key = SvegliaFragmentArgs.fromBundle(getArguments()).getSvegliaKey();

        String missioni = "";

        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);

        // Recupera il Set<String> associato alla chiave specificata
        Set<String> svegliaSet = sharedPref.getStringSet(key, new HashSet<>());

        // Cerca la stringa che indica le ripetizioni
        for (String val : svegliaSet) {
            if (!val.toString().isEmpty())
                if ((val.toString()).charAt(0) == 'm')
                    missioni = val.toString().substring(1);
        }


        final Button buttonInterrompi = view.findViewById(R.id.buttonInterrompi);
        final Button buttonGioca = view.findViewById(R.id.buttonGioca);
        barraSfide = view.findViewById(R.id.barraSfide);

        if (!missioni.equals("0000")) {
            buttonInterrompi.setVisibility(view.GONE);
            buttonGioca.setVisibility(View.VISIBLE);
            barraSfide.setVisibility(view.VISIBLE);
        }

        buttonInterrompi.setOnClickListener(v -> {

            stopAlarm();

            Navigation.findNavController(v).navigate(R.id.action_svegliaFragment_to_homeFragment);

        });




        imgCalcolaltrice=view.findViewById((R.id.sfida1));
        textCalc=view.findViewById((R.id.ripCalcolatrice));
        imgMemory=view.findViewById((R.id.sfida2));
        textMem=view.findViewById((R.id.ripMemory));
        imgScrivere=view.findViewById((R.id.sfida3));
        textScr=view.findViewById((R.id.ripScrivere));
        imgPassi=view.findViewById((R.id.sfida4));
        textPassi=view.findViewById((R.id.ripPassi));


        if (missioni.charAt(0)!='0') {
            imgCalcolaltrice.setImageDrawable(getResources().getDrawable(R.drawable.calcolatrice2));
            textCalc.setText("x" + missioni.charAt(0));
            ripCalcolatrice =  missioni.charAt(0) - '0'; }

        if (missioni.charAt(1)!='0') {
            imgMemory.setImageDrawable(getResources().getDrawable(R.drawable.memory2));
            textMem.setText("x" + missioni.charAt(1));
            ripMemory = Character.getNumericValue(missioni.charAt(1)); }


        if (missioni.charAt(2)!='0') {
            imgScrivere.setImageDrawable(getResources().getDrawable(R.drawable.scrivere2));
            textScr.setText("x" + missioni.charAt(2));
            ripScrivere = missioni.charAt(2) - '0'; }


        if (missioni.charAt(3)!='0') {
            imgPassi.setImageDrawable(getResources().getDrawable(R.drawable.passi2));
            textPassi.setText("x" + missioni.charAt(3));
            ripPassi = missioni.charAt(3) - '0'; }



        buttonGioca.setOnClickListener(v -> {

            stopAlarm();
            if(ripCalcolatrice>0) {
                SvegliaFragmentDirections.ActionSvegliaFragmentToCalcolatriceFragment action = SvegliaFragmentDirections.actionSvegliaFragmentToCalcolatriceFragment(ripCalcolatrice);
                Navigation.findNavController(view).navigate(action);}
            if(ripMemory>0) {
                SvegliaFragmentDirections.ActionSvegliaFragmentToMemoryFragment action = SvegliaFragmentDirections.actionSvegliaFragmentToMemoryFragment(ripMemory);
                Navigation.findNavController(view).navigate(action);}
            if(ripScrivere>0) {
                SvegliaFragmentDirections.ActionSvegliaFragmentToScrivereFragment action = SvegliaFragmentDirections.actionSvegliaFragmentToScrivereFragment(ripScrivere);
                Navigation.findNavController(view).navigate(action);}

        });

    }

    private void startAlarm() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }


    private void stopAlarm() {

        Log.d(TAG, "stop suono" + mediaPlayer + " ... " + mediaPlayer.isPlaying());

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();

            Log.d(TAG, "stop suono" + mediaPlayer + " ... " + mediaPlayer.isPlaying());

            mediaPlayer.release();
        }


    }
}