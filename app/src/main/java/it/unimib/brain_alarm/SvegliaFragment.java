package it.unimib.brain_alarm;

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

import it.unimib.brain_alarm.ui.SvegliaActivity;

public class SvegliaFragment extends Fragment {

    private static final String TAG = SvegliaFragment.class.getSimpleName();


    private MediaPlayer mediaPlayer;

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


        final Button buttonStopSuono = view.findViewById(R.id.buttonStopSuono);



        buttonStopSuono.setOnClickListener(v -> {

            stopAlarm();
        });






    }

    private void startAlarm() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }


    private void stopAlarm() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}