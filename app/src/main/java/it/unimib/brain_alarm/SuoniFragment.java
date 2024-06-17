package it.unimib.brain_alarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import it.unimib.brain_alarm.ui.HomeFragment;


public class SuoniFragment extends Fragment {

    private static final String TAG = SuoniFragment.class.getSimpleName();

    ImageButton imageButtonClassica;
    ImageButton imageButtonPianoforte;
    ImageButton imageButtonRadar;
    ImageButton imageButtonDolce;
    ImageButton imageButtonDigitale;

    boolean suonoAttivo = false;

    private MediaPlayer mediaPlayer;

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


        imageButtonClassica = view.findViewById(R.id.imageButtonClassica);
        imageButtonClassica.setOnClickListener(v -> {

            Log.d(TAG, "suonoAttivo " +  suonoAttivo);

            mediaPlayer = MediaPlayer.create(getContext(), R.raw.classica1);

            if (!suonoAttivo) {
                Log.d(TAG, "attivazione suono");
                suonoAttivo = true;
                startAlarm();
                imageButtonClassica.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else if (suonoAttivo) {
                Log.d(TAG, "disattivazione suono");
                Log.d(TAG, "disattivazione suono che ha playing " + mediaPlayer.isPlaying());
                suonoAttivo = false;
                stopAlarm(mediaPlayer);
                imageButtonClassica.setImageDrawable(getResources().getDrawable(R.drawable.pausa));
            }

        });


        imageButtonPianoforte = view.findViewById(R.id.imageButtonPianoforte);
        imageButtonPianoforte.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.pianoforte2);

            if (!suonoAttivo) {
                startAlarm();
                imageButtonPianoforte.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else {
                stopAlarm(mediaPlayer);
                imageButtonClassica.setImageDrawable(getResources().getDrawable(R.drawable.pausa));
            }
        });


        imageButtonRadar = view.findViewById(R.id.imageButtonRadar);
        imageButtonRadar.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.radar3);

            if (!suonoAttivo) {
                startAlarm();
                imageButtonRadar.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else {
                stopAlarm(mediaPlayer);
                imageButtonRadar.setImageDrawable(getResources().getDrawable(R.drawable.pausa));
            }
        });

        imageButtonDolce = view.findViewById(R.id.imageButtonDolce);
        imageButtonDolce.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.dolce4);

            if (!suonoAttivo) {
                startAlarm();
                imageButtonDolce.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else {
                stopAlarm(mediaPlayer);
                imageButtonDolce.setImageDrawable(getResources().getDrawable(R.drawable.pausa));
            }
        });


        imageButtonDigitale = view.findViewById(R.id.imageButtonDigitale);
        imageButtonDolce.setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.digitale5);

            if (!suonoAttivo) {
                startAlarm();
                imageButtonDigitale.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else {
                stopAlarm(mediaPlayer);
                imageButtonDigitale.setImageDrawable(getResources().getDrawable(R.drawable.pausa));
            }
        });






    }

    private void startAlarm() {

        //Log.d(TAG, "start suono");
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        Log.d(TAG, "suono Palying " + mediaPlayer.isPlaying());
    }

    private void stopAlarm(MediaPlayer mediaPlayer) {

        Log.d(TAG, "suono dentro stopAlarm");

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            Log.d(TAG, "suono dentro stopAlarm");
            mediaPlayer.stop();
            mediaPlayer.release();
        } else if (mediaPlayer == null) {
            Log.d(TAG, "suono mediaPlayer= null");
        } else if(!mediaPlayer.isPlaying()) {
            Log.d(TAG, "suono mediaPlayer.isPlaying()");
        }
    }




}