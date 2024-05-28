package it.unimib.brain_alarm;

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

public class SvegliaFragment extends Fragment {

    private static final String TAG = AggiungiActivity.class.getSimpleName();


    public SvegliaFragment() {
        // Required empty public constructor
    }


    public static SvegliaFragment newInstance() {
        SvegliaFragment fragment = new SvegliaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){


        final Button buttonAnnullaModifiche = view.findViewById(R.id.buttonAnnullaModificheSveglia);

        buttonAnnullaModifiche.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_svegliaFragment_to_homeFragment);

        });


        final Button buttonSalvaModifiche = view.findViewById(R.id.buttonSalvaModificheSveglia);

        buttonSalvaModifiche.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_svegliaFragment_to_homeFragment);

            //TODO modificare shared preferences della sveglia

        });




    }
}