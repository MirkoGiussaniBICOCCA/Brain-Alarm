package it.unimib.brain_alarm;


import static android.content.ContentValues.TAG;

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

import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MemoryFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    ImageButton b11;
    ImageButton b12;
    ImageButton b13;
    ImageButton b14;
    ImageButton b21;
    ImageButton b22;
    ImageButton b23;
    ImageButton b24;
    ImageButton b31;
    ImageButton b32;
    ImageButton b33;
    ImageButton b34;

    Integer[] cardArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    int image101, image102, image103, image104, image105, image106, image201, image202, image203, image204, image205, image206;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int turn = 1;

    int playerPoints = 0, cpuPoint = 0;

    int ripMemory;

    ImageView pallino1;
    ImageView pallino2;
    ImageView pallino3;
    ImageView pallino4;
    ImageView pallino5;




    public MemoryFragment() {
        // Required empty public constructor
    }


    public static MemoryFragment newInstance() {
        MemoryFragment fragment = new MemoryFragment();
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
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(v, savedInstanceState);


        String key = MemoryFragmentArgs.fromBundle(getArguments()).getKey();

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
                    Snackbar.make(v, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
                else if (val.equals("v0")){
                    Snackbar.make(v, "Vibrazione", Snackbar.LENGTH_SHORT).show();
                }
        }

        startAlarm();


        ripMemory = missioni.charAt(1) - '0';

        pallino1 = v.findViewById(R.id.pallino1);
        pallino2 = v.findViewById(R.id.pallino2);
        pallino3 = v.findViewById(R.id.pallino3);
        pallino4 = v.findViewById(R.id.pallino4);
        pallino5 = v.findViewById(R.id.pallino5);

        // Rendi visibili le ImageView in base al valore di ripMemory
        if (ripMemory >= 1) pallino1.setColorFilter(getResources().getColor(R.color.white));
        if (ripMemory >= 2) pallino2.setColorFilter(getResources().getColor(R.color.white));
        if (ripMemory >= 3) pallino3.setColorFilter(getResources().getColor(R.color.white));
        if (ripMemory >= 4) pallino4.setColorFilter(getResources().getColor(R.color.white));
        if (ripMemory >= 5) pallino5.setColorFilter(getResources().getColor(R.color.white));

        if(ripMemory > 0) {

            for (int i = 0; i < ripMemory; i++) {

                b11 = v.findViewById(R.id.m11);
                b12 = v.findViewById(R.id.m12);
                b13 = v.findViewById(R.id.m13);
                b14 = v.findViewById(R.id.m14);
                b21 = v.findViewById(R.id.m21);
                b22 = v.findViewById(R.id.m22);
                b23 = v.findViewById(R.id.m23);
                b24 = v.findViewById(R.id.m24);
                b31 = v.findViewById(R.id.m31);
                b32 = v.findViewById(R.id.m32);
                b33 = v.findViewById(R.id.m33);
                b34 = v.findViewById(R.id.m34);

                play(key);
            }
        }
        else {
            stopAlarm();
            stopVibration();
            MemoryFragmentDirections.ActionMemoryFragmentToScrivereFragment action = MemoryFragmentDirections.actionMemoryFragmentToScrivereFragment(key);
            Navigation.findNavController(v).navigate(action);
        }


    }

    private void play(String key){
        b11.setTag("0");
        b12.setTag("1");
        b13.setTag("2");
        b14.setTag("3");
        b21.setTag("4");
        b22.setTag("5");
        b23.setTag("6");
        b24.setTag("7");
        b31.setTag("8");
        b32.setTag("9");
        b33.setTag("10");
        b34.setTag("11");

        //imposta le immagini
        frontOfCardResources();
        //mescola la lista delle immagini
        Collections.shuffle((Arrays.asList(cardArray)));

        setCardListeners(key);
    }


    private void setCardListeners(String key){
        b11.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b11, theCard, key);
        });
        b12.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b12, theCard, key);
        });
        b13.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b13, theCard, key);
        });
        b14.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b14, theCard, key);
        });
        b21.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b21, theCard, key);
        });
        b22.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b22, theCard, key);
        });
        b23.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b23, theCard, key);
        });
        b24.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b24, theCard, key);
        });
        b31.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b31, theCard, key);
        });
        b32.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b32, theCard, key);
        });
        b33.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b33, theCard, key);
        });
        b34.setOnClickListener( v1 -> {
            int theCard = Integer.parseInt((String) v1.getTag());
            doStuff(b34, theCard, key);
        });
    }

    private void doStuff(ImageButton b, int card, String key) {

        Log.d(TAG, "dentro doStuff card " + card + " e card dentro array " + cardArray[card]);
        switch (cardArray[card]) {
            case 101:
                b.setImageDrawable(getResources().getDrawable(image101));
                break;
            case 102:
                b.setImageDrawable(getResources().getDrawable(image102));
                break;
            case 103:
                b.setImageDrawable(getResources().getDrawable(image103));
                break;
            case 104:
                b.setImageDrawable(getResources().getDrawable(image104));
                break;
            case 105:
                b.setImageDrawable(getResources().getDrawable(image105));
                break;
            case 106:
                b.setImageDrawable(getResources().getDrawable(image106));
                break;
            case 201:
                b.setImageDrawable(getResources().getDrawable(image201));
                break;
            case 202:
                b.setImageDrawable(getResources().getDrawable(image202));
                break;
            case 203:
                b.setImageDrawable(getResources().getDrawable(image103));
                break;
            case 204:
                b.setImageDrawable(getResources().getDrawable(image204));
                break;
            case 205:
                b.setImageDrawable(getResources().getDrawable(image205));
                break;
            case 206:
                b.setImageDrawable(getResources().getDrawable(image206));
                break;
        }

        if(cardNumber == 1) {
            firstCard = cardArray[card];
            if (firstCard>200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            b.setEnabled(false);

        } else if(cardNumber == 2) {
            secondCard = cardArray[card];
            if (secondCard>200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            disableAllButtons();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable () {
                @Override
                public void run() {
                        //cont4rolla se le immagini sono uguali
                    calculate(key);
                } } , 1000);

        }

    }

    private void disableAllButtons(){
        b11.setEnabled(false);
        b12.setEnabled(false);
        b13.setEnabled(false);
        b14.setEnabled(false);
        b21.setEnabled(false);
        b22.setEnabled(false);
        b23.setEnabled(false);
        b24.setEnabled(false);
        b31.setEnabled(false);
        b32.setEnabled(false);
        b33.setEnabled(false);
        b34.setEnabled(false);
    }

    private void resetAllButtons() {
        b11.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b12.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b13.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b14.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b21.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b22.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b23.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b24.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b31.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b32.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b33.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
        b34.setImageDrawable(getResources().getDrawable(R.drawable.domanda));
    }

    private void calculate(String key) {
        if (firstCard == secondCard) {
            setInvisible(clickedFirst);
            setInvisible(clickedSecond);

            if (turn == 1) {
                playerPoints++;
            } else if (turn == 2) {
                cpuPoint++;
            }
        } else {
            resetAllButtons();
        }

        if (turn == 1) {
            turn = 2;
        } else if (turn == 2) {
            turn = 1;
        }

        enableAllButtons();
        checked(key);
    }

    private void setInvisible(int clickedCard) {
        switch (clickedCard) {
            case 0:
                b11.setVisibility(View.INVISIBLE);
                break;
            case 1:
                b12.setVisibility(View.INVISIBLE);
                break;
            case 2:
                b13.setVisibility(View.INVISIBLE);
                break;
            case 3:
                b14.setVisibility(View.INVISIBLE);
                break;
            case 4:
                b21.setVisibility(View.INVISIBLE);
                break;
            case 5:
                b22.setVisibility(View.INVISIBLE);
                break;
            case 6:
                b23.setVisibility(View.INVISIBLE);
                break;
            case 7:
                b24.setVisibility(View.INVISIBLE);
                break;
            case 8:
                b31.setVisibility(View.INVISIBLE);
                break;
            case 9:
                b32.setVisibility(View.INVISIBLE);
                break;
            case 10:
                b33.setVisibility(View.INVISIBLE);
                break;
            case 11:
                b34.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void enableAllButtons() {
        b11.setEnabled(true);
        b12.setEnabled(true);
        b13.setEnabled(true);
        b14.setEnabled(true);
        b21.setEnabled(true);
        b22.setEnabled(true);
        b23.setEnabled(true);
        b24.setEnabled(true);
        b31.setEnabled(true);
        b32.setEnabled(true);
        b33.setEnabled(true);
        b34.setEnabled(true);
    }

    private void checked(String key) {
        if (b11.getVisibility() == View.INVISIBLE && b12.getVisibility() == View.INVISIBLE && b13.getVisibility() == View.INVISIBLE &&  b14.getVisibility() == View.INVISIBLE) {
            //qui se ho girato tutte le carte finisce il gioco
            Log.d(TAG, "ripMemory " + ripMemory);
            ripMemory--;
            // Rendi visibili le ImageView in base al valore di ripMemory
            if (ripMemory == 1) {pallino1.setImageDrawable(getResources().getDrawable(R.drawable.pallinochecked)); Log.d(TAG, "ripMemory set fatto " );}
            if (ripMemory == 2) pallino2.setImageDrawable(getResources().getDrawable(R.drawable.pallinochecked));
            if (ripMemory == 3) pallino3.setImageDrawable(getResources().getDrawable(R.drawable.pallinochecked));
            if (ripMemory == 4) pallino4.setImageDrawable(getResources().getDrawable(R.drawable.pallinochecked));
            if (ripMemory == 5) pallino5.setImageDrawable(getResources().getDrawable(R.drawable.pallinochecked));

            if (ripMemory > 0) {
                resetGame(key);
            } else {
                stopAlarm();
                stopVibration();
                // Fine gioco, vai al prossimo fragment
                MemoryFragmentDirections.ActionMemoryFragmentToScrivereFragment action = MemoryFragmentDirections.actionMemoryFragmentToScrivereFragment(key);
                Navigation.findNavController(getView()).navigate(action);
            }
        }
    }

    private void resetGame(String key) {
        playerPoints = 0;
        cpuPoint = 0;
        cardNumber = 1;
        turn = 1;



        b11.setVisibility(View.VISIBLE);
        b12.setVisibility(View.VISIBLE);
        b13.setVisibility(View.VISIBLE);
        b14.setVisibility(View.VISIBLE);
        b21.setVisibility(View.VISIBLE);
        b22.setVisibility(View.VISIBLE);
        b23.setVisibility(View.VISIBLE);
        b24.setVisibility(View.VISIBLE);
        b31.setVisibility(View.VISIBLE);
        b32.setVisibility(View.VISIBLE);
        b33.setVisibility(View.VISIBLE);
        b34.setVisibility(View.VISIBLE);

        resetAllButtons();

        Collections.shuffle(Arrays.asList(cardArray));
        setCardListeners(key);
    }

    private void frontOfCardResources() {
        image101 = R.drawable.italia;
        image102 = R.drawable.francia;
        image103 = R.drawable.germania;
        image104 = R.drawable.uk;
        image105 = R.drawable.usa;
        image106 = R.drawable.bandiera;
        image201 = R.drawable.italia;
        image202 = R.drawable.francia;
        image203 = R.drawable.germania;
        image204 = R.drawable.uk;
        image205 = R.drawable.usa;
        image206 = R.drawable.bandiera;
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