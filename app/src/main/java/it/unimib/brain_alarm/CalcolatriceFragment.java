package it.unimib.brain_alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class CalcolatriceFragment extends Fragment {


    private MediaPlayer mediaPlayer;

    TextView textQuestion;
    EditText editTextAnswer;
    Button buttonSubmit;

    int currentQuestion = 0;
    int rightAnswer = 0;
    final int TOTAL_QUESTIONS = 3;
    int remainingRepetitions;

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
        }

        startAlarm();

        remainingRepetitions = missioni.charAt(0) - '0';

        if (remainingRepetitions != 0) {

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
                            getARandomQuestion();
                            editTextAnswer.setText("");
                            Snackbar.make(view, "Corretto", Snackbar.LENGTH_SHORT).show();
                            remainingRepetitions--;
                            if (remainingRepetitions > 0) {
                                Snackbar.make(view, "Ripetizione completata! Ancora " + remainingRepetitions + " ripetizioni.", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(view, "Hai completato tutte le ripetizioni!", Snackbar.LENGTH_SHORT).show();
                                stopAlarm();
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

}
