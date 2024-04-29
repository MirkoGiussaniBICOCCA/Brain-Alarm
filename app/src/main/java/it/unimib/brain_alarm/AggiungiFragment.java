package it.unimib.brain_alarm;

import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_SVEGLIA;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.Set;

import it.unimib.brain_alarm.util.SharedPreferencesUtil;

public class AggiungiFragment extends Fragment {
    public AggiungiFragment() {
        // Required empty public constructor
    }

    private boolean isSfida = false;
    private boolean isClassica = false;

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

        final Button buttonClassica = view.findViewById(R.id.classica);
        buttonClassica.setOnClickListener(v -> {
            if (!isClassica) {
                isClassica = true;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.grigio)); }
            else {
                isClassica = false;
                buttonClassica.setBackgroundColor(getResources().getColor(R.color.azzurro));
            }
        } );

        final Button buttonSfida = view.findViewById(R.id.sfida);
        buttonSfida.setOnClickListener(v -> {
            if (!isSfida) {
                isSfida = true;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.grigio));
                Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_sfidaFragment); }
            else {
                //TODO quando imposto sfida e la salvo devo mantenere isSfida = true
                isSfida = false;
                buttonSfida.setBackgroundColor(getResources().getColor(R.color.arancione));
            }
        } );

        final Button buttonConferma = view.findViewById(R.id.conferma);
        buttonConferma.setOnClickListener(v -> {
            if (isSfida || isClassica)
                Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
            else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.mxModalità), Snackbar.LENGTH_LONG).show();

        } );

        final Button buttonAnnulla = view.findViewById(R.id.annulla);
        buttonAnnulla.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_mainActivity);
        } );

        final Button buttoneSuono = view.findViewById(R.id.bottoneSuono);
        buttoneSuono.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_aggiungiFragment_to_suoniFragment);

        });
    }


}
