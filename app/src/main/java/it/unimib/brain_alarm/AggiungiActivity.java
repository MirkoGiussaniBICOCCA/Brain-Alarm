package it.unimib.brain_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class AggiungiActivity extends AppCompatActivity {
    private static final String TAG = AggiungiActivity.class.getSimpleName();

    private TextInputLayout textInput;


    private TextInputLayout textInputLayoutEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi);  //da qua cambio l'avvio del programma

        final Button buttonConferma = findViewById(R.id.conferma);

        textInput = findViewById(R.id.nomeSveglia);

        buttonConferma.setOnClickListener(v -> {

            String nomeSveglia = textInput.getEditText().getText().toString();

            // messaggio TAG
            Log.d(TAG, "nome sveglia: " + nomeSveglia);
        });
    }
}