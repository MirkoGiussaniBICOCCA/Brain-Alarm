package it.unimib.brain_alarm;

import static androidx.fragment.app.FragmentManager.TAG;

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

        String ripMissioni = ScrivereFragmentArgs.fromBundle(getArguments()).getRipMissioni();

        ripScrivere = ripMissioni.charAt(2) - '0';

        if(ripScrivere > 0) {
            imageView = v.findViewById(R.id.imageView);
            editText = v.findViewById(R.id.editText);
            submitButton = v.findViewById(R.id.submitButton);
            resultTextView = v.findViewById(R.id.resultTextView);

            imagesMap = new HashMap<>();
            imagesMap.put(R.drawable.scrivere2, "Immagine 1");
            imagesMap.put(R.drawable.passi2, "Immagine 2");
            imagesMap.put(R.drawable.calcolatrice2, "Immagine 3");

            showRandomImage();

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer();
                }
            });
        }
        else {
            Navigation.findNavController(v).navigate(R.id.action_scrivereFragment_to_homeFragment);
        }

    }

    private void showRandomImage() {
        if (imageShownCount >= ripScrivere) {
            Navigation.findNavController(getView()).navigate(R.id.action_scrivereFragment_to_homeFragment);
            return;
        }

        Object[] keys = imagesMap.keySet().toArray();
        currentImageResource = (int) keys[new Random().nextInt(keys.length)];
        imageView.setImageResource(currentImageResource);
        editText.setText("");
        resultTextView.setText("");
    }

    private void checkAnswer() {
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
                showRandomImage();
            }
        }, 2000);
    }

}