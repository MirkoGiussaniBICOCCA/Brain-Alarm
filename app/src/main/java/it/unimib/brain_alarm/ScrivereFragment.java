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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class ScrivereFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;


    private int imageShownCount = 0;

    int ripScrivere;
    private ImageView imageView;
    private EditText editText;
    private Button submitButton;
    private TextView resultTextView;

    private Map<Integer, String> imagesMap;
    private int currentImageResource;


    ImageView pallino1;
    ImageView pallino2;
    ImageView pallino3;
    ImageView pallino4;
    ImageView pallino5;

    public ScrivereFragment() {
        // Required empty public constructor
    }


    public static ScrivereFragment newInstance() {
        ScrivereFragment fragment = new ScrivereFragment();
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
        return inflater.inflate(R.layout.fragment_scrivere, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(v, savedInstanceState);

        String key = ScrivereFragmentArgs.fromBundle(getArguments()).getKey();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        // Recupera il Set<String> associato alla chiave specificata
        Set<String> svegliaSet = sharedPref.getStringSet(key, new HashSet<>());
        String missioni="00000";
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
                    Snackbar.make(v, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
                else if (val.equals("v0")){
                    Snackbar.make(v, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
        }

        startAlarm();

        ripScrivere = missioni.charAt(2) - '0';
        pallino1 = v.findViewById(R.id.pallino1);
        pallino2 = v.findViewById(R.id.pallino2);
        pallino3 = v.findViewById(R.id.pallino3);
        pallino4 = v.findViewById(R.id.pallino4);
        pallino5 = v.findViewById(R.id.pallino5);

        // Rendi visibili le ImageView in base al valore di ripMemory
        if (ripScrivere >= 1) pallino1.setColorFilter(getResources().getColor(R.color.white));
        if (ripScrivere >= 2) pallino2.setColorFilter(getResources().getColor(R.color.white));
        if (ripScrivere >= 3) pallino3.setColorFilter(getResources().getColor(R.color.white));
        if (ripScrivere >= 4) pallino4.setColorFilter(getResources().getColor(R.color.white));
        if (ripScrivere >= 5) pallino5.setColorFilter(getResources().getColor(R.color.white));


        if(ripScrivere > 0) {
            imageView = v.findViewById(R.id.imageView);
            editText = v.findViewById(R.id.editText);
            submitButton = v.findViewById(R.id.submitButton);
            resultTextView = v.findViewById(R.id.resultTextView);

            imagesMap = new HashMap<>();
            imagesMap.put(R.drawable.treduecinquefb, "325fb");
            imagesMap.put(R.drawable.dadi, "dadi");
            imagesMap.put(R.drawable.dmw8n, "dmw8n");
            imagesMap.put(R.drawable.gatto, "gatto");
            imagesMap.put(R.drawable.macchina, "macchina");
            imagesMap.put(R.drawable.nn4wx, "nn4wx");
            imagesMap.put(R.drawable.pdw38, "pdw38");
            imagesMap.put(R.drawable.pinguino, "pinguino");
            imagesMap.put(R.drawable.super_mario, "super mario");
            imagesMap.put(R.drawable.w46ep, "w46ep");

            showRandomImage(key);

            String finalMissioni = missioni;
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(finalMissioni);
                }
            });
        }
        else {
            stopAlarm();
            stopVibration();
            ScrivereFragmentDirections.ActionScrivereFragmentToTrisFragment action = ScrivereFragmentDirections.actionScrivereFragmentToTrisFragment(key);
            Navigation.findNavController(getView()).navigate(action);
        }

    }

    private void showRandomImage(String key) {
        if (imageShownCount >= ripScrivere) {
            stopAlarm();
            stopVibration();
            ScrivereFragmentDirections.ActionScrivereFragmentToTrisFragment action = ScrivereFragmentDirections.actionScrivereFragmentToTrisFragment(key);
            Navigation.findNavController(getView()).navigate(action);
            return;
        }

        Object[] keys = imagesMap.keySet().toArray();
        currentImageResource = (int) keys[new Random().nextInt(keys.length)];
        imageView.setImageResource(currentImageResource);
        editText.setText("");
        resultTextView.setText("");
    }

    private void checkAnswer(String key) {
        String userAnswer = editText.getText().toString().trim();
        String correctAnswer = imagesMap.get(currentImageResource);

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            resultTextView.setText("Corretto!");
        } else {
            resultTextView.setText("Sbagliato. Era: " + correctAnswer);
        }

        imageShownCount++;

        if (imageShownCount == 1) {pallino1.setColorFilter(getResources().getColor(R.color.fucsia));}
        if (imageShownCount == 2) {pallino2.setColorFilter(getResources().getColor(R.color.fucsia));}
        if (imageShownCount == 3) {pallino3.setColorFilter(getResources().getColor(R.color.fucsia));}
        if (imageShownCount == 4) {pallino4.setColorFilter(getResources().getColor(R.color.fucsia));}
        if (imageShownCount == 5) {pallino5.setColorFilter(getResources().getColor(R.color.fucsia));}


        // Mostra una nuova immagine dopo 2 secondi
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showRandomImage(key);
            }
        }, 2000);
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