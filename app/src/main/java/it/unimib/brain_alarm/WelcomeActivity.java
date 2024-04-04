package it.unimib.brain_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private TextInputLayout textInput;


    private TextInputLayout textInputLayoutEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);  //da qua cambio l'avvio del programma

        final Button buttonConferma = findViewById(R.id.conferma);
        textInput = findViewById(R.id.nomeSveglia);

        buttonConferma.setOnClickListener(v -> {

            String nomeSveglia = textInput.getEditText().getText().toString();

            // messaggio TAG
            Log.d(TAG, "nome sveglia: " + nomeSveglia);
        });
    }
}