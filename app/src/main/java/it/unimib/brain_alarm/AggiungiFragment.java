package it.unimib.brain_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AggiungiFragment extends Fragment {
    public AggiungiFragment() {
        // Required empty public constructor
    }


    public static AggiungiFragment newInstance(String param1, String param2) {
        AggiungiFragment fragment = new AggiungiFragment();
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



        return inflater.inflate(R.layout.fragment_aggiungi, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        final Button buttonSfida = view.findViewById(R.id.sfida);
        buttonSfida.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_sfidaFragment);
        } );

        final Button buttonConferma = view.findViewById(R.id.conferma);
        buttonConferma.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_homeFragment);
        } );

        final Button buttonAnnulla = view.findViewById(R.id.annulla);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_homeFragment);
        } );

    }


}
