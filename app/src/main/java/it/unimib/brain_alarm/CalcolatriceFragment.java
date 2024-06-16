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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class CalcolatriceFragment extends Fragment {


    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    TextView textQuestion;
    EditText editTextAnswer;
    Button buttonSubmit;


    ImageView pallino1;
    ImageView pallino2;
    ImageView pallino3;
    ImageView pallino4;
    ImageView pallino5;


    int currentQuestion = 0;
    int rightAnswer = 0;
    final int TOTAL_QUESTIONS = 3;

    int ripCalc;
    int count;

    public CalcolatriceFragment() {
        // Required empty public constructor
    }

    public static CalcolatriceFragment newInstance() {
        CalcolatriceFragment fragment = new CalcolatriceFragment();
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
        return inflater.inflate(R.layout.fragment_calcolatrice, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String key = CalcolatriceFragmentArgs.fromBundle(getArguments()).getKey();

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
                    Snackbar.make(view, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
                else if (val.equals("v0")){
                    Snackbar.make(view, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
        }

        startAlarm();


        ripCalc = missioni.charAt(0) - '0';
        count = 0;

        pallino1 = view.findViewById(R.id.pallino1);
        pallino2 = view.findViewById(R.id.pallino2);
        pallino3 = view.findViewById(R.id.pallino3);
        pallino4 = view.findViewById(R.id.pallino4);
        pallino5 = view.findViewById(R.id.pallino5);

        // Rendi visibili le ImageView in base al valore di ripMemory
        if (ripCalc >= 1) pallino1.setColorFilter(getResources().getColor(R.color.white));
        if (ripCalc >= 2) pallino2.setColorFilter(getResources().getColor(R.color.white));
        if (ripCalc >= 3) pallino3.setColorFilter(getResources().getColor(R.color.white));
        if (ripCalc >= 4) pallino4.setColorFilter(getResources().getColor(R.color.white));
        if (ripCalc >= 5) pallino5.setColorFilter(getResources().getColor(R.color.white));

        if (ripCalc > 0) {

            textQuestion = view.findViewById(R.id.textQuestion);
            editTextAnswer = view.findViewById(R.id.editTextAnswer);
            buttonSubmit = view.findViewById(R.id.buttonSubmit);

            getARandomQuestion();

            buttonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String answerText = editTextAnswer.getText().toString();
                    if (!answerText.isEmpty()) {
                        int userAnswer = Integer.parseInt(answerText);
                        if (userAnswer == rightAnswer) {
                            editTextAnswer.setText("");
                            Snackbar.make(view, "Corretto", Snackbar.LENGTH_SHORT).show();
                            count++;
                            if (count == 1) {pallino1.setColorFilter(getResources().getColor(R.color.fucsia));}
                            if (count == 2) {pallino2.setColorFilter(getResources().getColor(R.color.fucsia));}
                            if (count == 3) {pallino3.setColorFilter(getResources().getColor(R.color.fucsia));}
                            if (count == 4) {pallino4.setColorFilter(getResources().getColor(R.color.fucsia));}
                            if (count == 5) {pallino5.setColorFilter(getResources().getColor(R.color.fucsia));}

                            if (count < ripCalc) {
                                Snackbar.make(view, "Ripetizione completata! Ancora " + (ripCalc-count) + " ripetizioni.", Snackbar.LENGTH_SHORT).show();
                                getARandomQuestion();
                            } else {
                                Snackbar.make(view, "Hai completato tutte le ripetizioni!", Snackbar.LENGTH_SHORT).show();
                                stopAlarm();
                                stopVibration();
                                CalcolatriceFragmentDirections.ActionCalcolatriceFragmentToMemoryFragment action = CalcolatriceFragmentDirections.actionCalcolatriceFragmentToMemoryFragment(key);
                                Navigation.findNavController(view).navigate(action);
                            }
                        } else {
                            Snackbar.make(view, "Risposta sbagliata, riprova", Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(view, "Per favore, inserisci una risposta", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });



        } else {
            stopAlarm();
            stopVibration();
            CalcolatriceFragmentDirections.ActionCalcolatriceFragmentToMemoryFragment action = CalcolatriceFragmentDirections.actionCalcolatriceFragmentToMemoryFragment(key);
            Navigation.findNavController(view).navigate(action);
        }
    }

    private void getARandomQuestion() {
        int firstNumber = new Random().nextInt(100);
        int secondNumber = new Random().nextInt(100);
        int operation = new Random().nextInt(2) + 1;

        if (operation == 1) {
            rightAnswer = firstNumber + secondNumber;
            textQuestion.setText(firstNumber + " + " + secondNumber + " = ?");
        } else if (operation == 2) {
            if (firstNumber < secondNumber) {
                rightAnswer = secondNumber - firstNumber;
                textQuestion.setText(secondNumber + " - " + firstNumber + " = ?");
            } else {
                rightAnswer = firstNumber - secondNumber;
                textQuestion.setText(firstNumber + " - " + secondNumber + " = ?");
            }
        }
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
