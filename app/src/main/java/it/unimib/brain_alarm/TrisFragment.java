package it.unimib.brain_alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class TrisFragment extends Fragment {

    int ripTris;

    private char[][] board = new char[3][3];
    private boolean playerTurn = true; // true if it's player's turn, false if it's computer's turn
    private Random random = new Random();

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

    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(v, savedInstanceState);

        String ripMissioni = ScrivereFragmentArgs.fromBundle(getArguments()).getRipMissioni();

        ripTris = ripMissioni.charAt(3) - '0';

        if(ripTris>0) {



            GridLayout gridLayout = v.findViewById(R.id.gridLayout);

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
                            } else {
                                playerTurn = false;
                                computerMove(v);
                            }
                        }
                    }
                });
            }

        }
        else {
            Navigation.findNavController(getView()).navigate(R.id.action_trisFragment_to_homeFragment);
        }


    }


    private void computerMove(View v) {
        if (isBoardFull()) {
            Snackbar.make(v, "Pareggio", Snackbar.LENGTH_SHORT).show();
            return;
        }

        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '\0');

        GridLayout gridLayout = v.findViewById(R.id.gridLayout);
        Button button = (Button) gridLayout.getChildAt(row * 3 + col);
        button.setText("O");
        board[row][col] = 'O';

        if (checkWinner()) {
            Snackbar.make(v, "Il computer ha vinto", Snackbar.LENGTH_SHORT).show();
        } else {
            playerTurn = true;
        }
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

}