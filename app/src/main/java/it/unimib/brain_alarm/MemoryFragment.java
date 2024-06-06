package it.unimib.brain_alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MemoryFragment extends Fragment {

    int ripMemory;

    public MemoryFragment() {
        // Required empty public constructor
    }


    public static MemoryFragment newInstance() {
        MemoryFragment fragment = new MemoryFragment();
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
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(v, savedInstanceState);

        ripMemory = MemoryFragmentArgs.fromBundle(getArguments()).getRipMemory();

        Navigation.findNavController(v).navigate(R.id.action_memoryFragment_to_svegliaFragment);
    }
}