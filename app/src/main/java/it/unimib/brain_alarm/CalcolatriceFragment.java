package it.unimib.brain_alarm;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CalcolatriceFragment extends Fragment {

    TextView ripcalc;

    public CalcolatriceFragment() {
        // Required empty public constructor
    }


    public static CalcolatriceFragment newInstance() {
        CalcolatriceFragment fragment = new CalcolatriceFragment();
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
        return inflater.inflate(R.layout.fragment_calcolatrice, container, false);
    }

   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        String ripMissioni = CalcolatriceFragmentArgs.fromBundle(getArguments()).getRipMissioni();

        ripcalc = view.findViewById(R.id.textRipetizioni);
        ripcalc.setText(ripMissioni);

        Navigation.findNavController(view).navigate(R.id.action_calcolatriceFragment_to_memoryFragment);
    }

}