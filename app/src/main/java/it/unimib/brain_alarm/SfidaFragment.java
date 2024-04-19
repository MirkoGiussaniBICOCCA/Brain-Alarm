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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SfidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SfidaFragment extends Fragment {

    public SfidaFragment() {
        // Required empty public constructor
    }

    public static SfidaFragment newInstance(String param1, String param2) {
        SfidaFragment fragment = new SfidaFragment();
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
        return inflater.inflate(R.layout.fragment_sfida, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final Button buttonSalva = view.findViewById(R.id.SalvaSfida);
        buttonSalva.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_sfidaFragment_to_aggiungiFragment);
        });
    }




}



