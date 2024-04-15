package it.unimib.brain_alarm;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = AggiungiActivity.class.getSimpleName();

    private TextInputLayout textInput;

    private TextInputLayout textInputLayoutEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button buttonAggiungi = findViewById(R.id.bottoneAggiungiSveglia);

         buttonAggiungi.setOnClickListener(v -> {
             Log.d(TAG, "Cliccato bottone aggiungi");

             Intent intent = new Intent(this, AggiungiActivity.class);

             //startActivityBasedOnCondition(AggiungiActivity.class,
             //R.id.navigate_to_newsPreferencesActivity);

             startActivity(intent);
             finish();
        });
    }
}

    /*private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination){
        if(USE_NAVIGATION_COMPONENT){
            Navigation.findNavController(requireView()).navigate(destination);
        }else{
            Intent intent=new Intent(requireContext(),destinationActivity);
            startActivity(intent);
        }
        requireActivity().finish();
        }*/


