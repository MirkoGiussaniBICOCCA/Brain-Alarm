package it.unimib.brain_alarm;


import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import it.unimib.brain_alarm.util.SharedPreferencesUtil;


public class ProfiloFragment extends Fragment {

    private static final String TAG = ProfiloFragment.class.getSimpleName();

    TextView nomeProfilo;


    public ProfiloFragment() {
        // Required empty public constructor
    }

    public static ProfiloFragment newInstance(String param1, String param2) {
        ProfiloFragment fragment = new ProfiloFragment();
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
        return inflater.inflate(R.layout.fragment_profilo, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        nomeProfilo = view.findViewById(R.id.nomeProfilo);
        nomeProfilo.setText("Ciao " + sharedPref.getString("nome", null));


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            ((ImageView) view.findViewById(R.id.fotoProfilo)).setImageBitmap(bitmap);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


        Button fotoButton = view.findViewById(R.id.bottoneFoto);

        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass in the mime type you want to let the user select as the input
                mGetContent.launch("image/*");
            }
        });


    }





}