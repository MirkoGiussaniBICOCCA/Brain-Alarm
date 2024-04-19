package it.unimib.brain_alarm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link riposoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class riposoFragment extends Fragment {


    public riposoFragment() {
        // Required empty public constructor
    }


    public static riposoFragment newInstance(String param1, String param2) {
        riposoFragment fragment = new riposoFragment();
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
        return inflater.inflate(R.layout.fragment_riposo, container, false);
    }
}