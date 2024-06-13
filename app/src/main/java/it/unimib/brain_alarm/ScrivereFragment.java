package it.unimib.brain_alarm;

import static androidx.fragment.app.FragmentManager.TAG;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;



public class ScrivereFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    private int imageShownCount = 0;

    int ripScrivere;
    private ImageView imageView;
    private EditText editText;
    private Button submitButton;
    private TextView resultTextView;

    private Map<Integer, String> imagesMap;
    private int currentImageResource;


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

    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(v, savedInstanceState);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.suono2);
        startAlarm();

        String ripMissioni = ScrivereFragmentArgs.fromBundle(getArguments()).getRipMissioni();

        ripScrivere = ripMissioni.charAt(2) - '0';

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

            showRandomImage(ripMissioni);

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(ripMissioni);
                }
            });
        }
        else {
            stopAlarm();
            ScrivereFragmentDirections.ActionScrivereFragmentToTrisFragment action = ScrivereFragmentDirections.actionScrivereFragmentToTrisFragment(ripMissioni);
            Navigation.findNavController(getView()).navigate(action);
        }

    }

    private void showRandomImage(String ripMissioni) {
        if (imageShownCount >= ripScrivere) {
            stopAlarm();
            ScrivereFragmentDirections.ActionScrivereFragmentToTrisFragment action = ScrivereFragmentDirections.actionScrivereFragmentToTrisFragment(ripMissioni);
            Navigation.findNavController(getView()).navigate(action);
            return;
        }

        Object[] keys = imagesMap.keySet().toArray();
        currentImageResource = (int) keys[new Random().nextInt(keys.length)];
        imageView.setImageResource(currentImageResource);
        editText.setText("");
        resultTextView.setText("");
    }

    private void checkAnswer(String ripMissioni) {
        String userAnswer = editText.getText().toString().trim();
        String correctAnswer = imagesMap.get(currentImageResource);

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            resultTextView.setText("Corretto!");
        } else {
            resultTextView.setText("Sbagliato. Era: " + correctAnswer);
        }

        imageShownCount++;

        // Mostra una nuova immagine dopo 2 secondi
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showRandomImage(ripMissioni);
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

}