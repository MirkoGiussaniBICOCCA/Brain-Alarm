package it.unimib.brain_alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

public class SvegliaFragment extends Fragment {

    private static final String TAG = SvegliaFragment.class.getSimpleName();


    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    LinearLayout barraSfide;
    ImageView imgCalcolaltrice;
    TextView textCalc;

    ImageView imgMemory;
    TextView textMem;

    ImageView imgScrivere;
    TextView textScr;

    ImageView imgTris;
    TextView textTris;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        super.onViewCreated(view, savedInstanceState);


        String key = SvegliaFragmentArgs.fromBundle(getArguments()).getSvegliaKey();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        // Recupera il Set<String> associato alla chiave specificata
        Set<String> svegliaSet = sharedPref.getStringSet(key, new HashSet<>());
        String missioni="00000";
        // Cerca la stringa che indica le ripetizioni
        for (String val : svegliaSet) {
            if (!val.toString().isEmpty())
                if ((val.toString()).charAt(0) == 'm')
                    missioni = val.toString().substring(1);
                else if (val.equals("Classica")){
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.classica1);
                }
                else if (val.equals("Pianoforte")){
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.pianoforte2);
                }
                else if (val.equals("Radar")){
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.radar3);
                }
                else if (val.equals("Dolce")){
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.dolce4);
                }
                else if (val.equals("Digitale")){
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.digitale5);
                }
                else if (val.equals("v1")){
                    startVibration(getContext());
                    Snackbar.make(view, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
                else if (val.equals("v0")){
                    Snackbar.make(view, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
        }

        startAlarm();

        final Button buttonInterrompi = view.findViewById(R.id.buttonInterrompi);
        final Button buttonGioca = view.findViewById(R.id.buttonGioca);
        barraSfide = view.findViewById(R.id.barraSfide);

        if (!missioni.equals("00000")) {
            buttonInterrompi.setVisibility(view.GONE);
            buttonGioca.setVisibility(View.VISIBLE);
            barraSfide.setVisibility(view.VISIBLE);
        }

        buttonInterrompi.setOnClickListener(v -> {

            stopAlarm();
            stopVibration();

            Log.d(TAG,"Click interrompi");
            Navigation.findNavController(v).navigate(R.id.action_svegliaFragment_to_homeFragment);

        });




        imgCalcolaltrice=view.findViewById((R.id.sfida1));
        textCalc=view.findViewById((R.id.ripCalcolatrice));
        imgMemory=view.findViewById((R.id.sfida2));
        textMem=view.findViewById((R.id.ripMemory));
        imgScrivere=view.findViewById((R.id.sfida3));
        textScr=view.findViewById((R.id.ripScrivere));
        imgTris=view.findViewById((R.id.sfida4));
        textTris=view.findViewById((R.id.ripTris));
        imgPassi=view.findViewById((R.id.sfida5));
        textPassi=view.findViewById((R.id.ripPassi));


        if (missioni.charAt(0)!='0') {
            imgCalcolaltrice.setImageDrawable(getResources().getDrawable(R.drawable.calcolatrice2));
            textCalc.setText("x" + missioni.charAt(0));}

        if (missioni.charAt(1)!='0') {
            imgMemory.setImageDrawable(getResources().getDrawable(R.drawable.memory2));
            textMem.setText("x" + missioni.charAt(1));}


        if (missioni.charAt(2)!='0') {
            imgScrivere.setImageDrawable(getResources().getDrawable(R.drawable.scrivere2));
            textScr.setText("x" + missioni.charAt(2)); }

        if (missioni.charAt(3)!='0') {
            imgTris.setImageDrawable(getResources().getDrawable(R.drawable.tris2));
            textTris.setText("x" + missioni.charAt(3)); }


        if (missioni.charAt(4)!='0') {
            imgPassi.setImageDrawable(getResources().getDrawable(R.drawable.passi2));
            textPassi.setText("x" + missioni.charAt(4)); }


        String finalMissioni = missioni;
        buttonGioca.setOnClickListener(v -> {
            stopAlarm();
            stopVibration();
            SvegliaFragmentDirections.ActionSvegliaFragmentToCalcolatriceFragment action = SvegliaFragmentDirections.actionSvegliaFragmentToCalcolatriceFragment(key);
            Navigation.findNavController(view).navigate(action);

        });

    }



    private void startAlarm() {

        //Log.d(TAG, "start suono");
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void stopAlarm() {

        //Log.d(TAG, "stop suono" + mediaPlayer + " ... " + mediaPlayer.isPlaying());

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();

            mediaPlayer.release();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startVibration(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            long[] pattern = {0, 1000, 1000}; // wait 0ms, vibrate 1000ms, sleep 1000ms
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0)); // 0 repeats indefinitely
        }
    }

    private void stopVibration() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}