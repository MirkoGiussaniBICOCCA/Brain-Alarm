package it.unimib.brain_alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TrisFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    int ripTris;
    int currentGameCount = 0;

    private char[][] board = new char[3][3];
    private boolean playerTurn = true; // true se tocca a te, false se tocca al pc
    private Random random = new Random();

    private GridLayout gridLayout;

    public TrisFragment() {
        // Required empty public constructor
    }

    public static TrisFragment newInstance() {
        TrisFragment fragment = new TrisFragment();
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
        return inflater.inflate(R.layout.fragment_tris, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        String key = TrisFragmentArgs.fromBundle(getArguments()).getKey();

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
        ripTris = missioni.charAt(3) - '0';

        if (ripTris > 0) {
            gridLayout = v.findViewById(R.id.gridLayout);

            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                final int row = i / 3;
                final int col = i % 3;
                Button button = (Button) gridLayout.getChildAt(i);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playerTurn && board[row][col] == '\0') {
                            ((Button) v).setText("X");
                            board[row][col] = 'X';
                            if (checkWinner()) {
                                Snackbar.make(v, "Hai vinto", Snackbar.LENGTH_SHORT).show();
                                endGame(v);
                            } else {
                                playerTurn = false;
                                // Delay the computer's move by 1 second
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        computerMove(v);
                                    }
                                }, 1000);
                            }
                        }
                    }
                });
            }
        } else {
            stopAlarm();
            Navigation.findNavController(v).navigate(R.id.action_trisFragment_to_homeFragment);
        }
    }

    private void computerMove(View v) {
        if (isBoardFull()) {
            Snackbar.make(v, "Pareggio", Snackbar.LENGTH_SHORT).show();
            endGame(v);
            return;
        }

        int[] move = findBestMove();
        int row = move[0];
        int col = move[1];

        if (gridLayout == null) {
            Log.e("TrisFragment", "GridLayout is null in computerMove");
            return;
        }

        Button button = (Button) gridLayout.getChildAt(row * 3 + col);
        button.setText("O");
        board[row][col] = 'O';

        if (checkWinner()) {
            Snackbar.make(v, "Il computer ha vinto", Snackbar.LENGTH_SHORT).show();
            endGame(v);
        } else {
            playerTurn = true;
        }
    }

    private void endGame(View v) {
        currentGameCount++;
        if (currentGameCount < ripTris) {
            resetBoard();
        } else {
            stopAlarm();
            Navigation.findNavController(v).navigate(R.id.action_trisFragment_to_homeFragment);
        }
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
                Button button = (Button) gridLayout.getChildAt(i * 3 + j);
                button.setText("");
            }
        }
        playerTurn = true;
    }

    private int[] findBestMove() {
        // Check if computer can block player
        for (int i = 0; i < 3; i++) {
            // Check rows
            if (board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == '\0') return new int[]{i, 2};
            if (board[i][1] == 'X' && board[i][2] == 'X' && board[i][0] == '\0') return new int[]{i, 0};
            if (board[i][0] == 'X' && board[i][2] == 'X' && board[i][1] == '\0') return new int[]{i, 1};

            // Check columns
            if (board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == '\0') return new int[]{2, i};
            if (board[1][i] == 'X' && board[2][i] == 'X' && board[0][i] == '\0') return new int[]{0, i};
            if (board[0][i] == 'X' && board[2][i] == 'X' && board[1][i] == '\0') return new int[]{1, i};
        }

        // Check diagonals
        if (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == '\0') return new int[]{2, 2};
        if (board[1][1] == 'X' && board[2][2] == 'X' && board[0][0] == '\0') return new int[]{0, 0};
        if (board[0][0] == 'X' && board[2][2] == 'X' && board[1][1] == '\0') return new int[]{1, 1};

        if (board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == '\0') return new int[]{2, 0};
        if (board[1][1] == 'X' && board[2][0] == 'X' && board[0][2] == '\0') return new int[]{0, 2};
        if (board[0][2] == 'X' && board[2][0] == 'X' && board[1][1] == '\0') return new int[]{1, 1};

        // If no blocking move, choose a random empty cell
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '\0');

        return new int[]{row, col};
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '\0') {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '\0') {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\0') {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\0') {
            return true;
        }
        return false;
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
