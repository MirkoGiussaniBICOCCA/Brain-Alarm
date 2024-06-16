package it.unimib.brain_alarm;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class SuoniFragment extends Fragment {

    ImageButton imageButtonClassica;


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
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.classica1);
            startAlarm();
            //imageButtonClassica.setImageDrawable(getResources().getDrawable(R.id.pausa));

            stopAlarm();
            //imageButtonClassica.setImageDrawable(getResources().getDrawable(R.id.start));

        });


        mediaPlayer = MediaPlayer.create(getContext(), R.raw.pianoforte2);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.radar3);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.dolce4);

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.digitale5);





        /*
        final Button buttonMaps = view.findViewById(R.id.maps);
        buttonMaps.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } );
        */





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