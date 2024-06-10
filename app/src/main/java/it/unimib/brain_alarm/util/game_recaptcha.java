package it.unimib.brain_alarm.util;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import it.unimib.brain_alarm.R;

public class game_recaptcha extends AppCompatActivity {
    private ImageView imageView;
    private EditText editText;
    private Button submitButton;
    private TextView resultTextView;

    private Map<Integer, String> imagesMap;
    private int currentImageResource;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);

        imagesMap = new HashMap<>();
        imagesMap.put(R.drawable.image1, "Immagine 1");
        imagesMap.put(R.drawable.image2, "Immagine 2");
        imagesMap.put(R.drawable.image3, "Immagine 3");

        showRandomImage();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void showRandomImage() {
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

        // Mostra una nuova immagine dopo 2 secondi
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showRandomImage();
            }
        }, 2000);
    }

}
