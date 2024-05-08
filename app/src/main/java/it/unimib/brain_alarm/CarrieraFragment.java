package it.unimib.brain_alarm;

import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
import static it.unimib.brain_alarm.util.Constants.UNITED_KINGDOM;
import static it.unimib.brain_alarm.util.Constants.UNITED_STATES;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsSource;
import it.unimib.brain_alarm.ui.MainActivity;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;


public class CarrieraFragment extends Fragment {

    private static final String TAG = CarrieraFragment.class.getSimpleName();
    private static String nomeProfilo;
    TextView nuovoNome;

    private TextInputLayout inputLayoutNome;
    private LinearLayout layoutCambiaNome;

    public CarrieraFragment() {
        // Required empty public constructor
    }

    public static CarrieraFragment newInstance(String param1, String param2) {
        CarrieraFragment fragment = new CarrieraFragment();
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
        return inflater.inflate(R.layout.fragment_carriera, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button buttonCambiaNome = view.findViewById(R.id.cambiaNome);
        final Button buttonSalvaNome = view.findViewById(R.id.salvaNome);
        layoutCambiaNome = view.findViewById(R.id.linearLayoutCambiaNome);
        inputLayoutNome = view.findViewById(R.id.inputLayoutNome);

        buttonCambiaNome.setOnClickListener(v -> {
            layoutCambiaNome.setVisibility(view.VISIBLE);
        });

        nuovoNome = view.findViewById(R.id.nomeProfilo);

        //salvo nuovoNome nel file shared preferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        nomeProfilo = sharedPref.getString("nomeProfilo", null);

        if(nomeProfilo != null)
            nuovoNome.setText("Ciao " + nomeProfilo);

        buttonSalvaNome.setOnClickListener(v -> {

            nomeProfilo = inputLayoutNome.getEditText().getText().toString();

            if (nomeProfilo!=null) {
                saveInformation();

                //visualizzare nuovo nome
                nuovoNome.setText("Ciao " + sharedPref.getString("nomeProfilo", null));
                layoutCambiaNome.setVisibility(view.GONE);
            } else
                nuovoNome.setText("null");
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            ((ImageView) view.findViewById(R.id.carriera)).setImageBitmap(bitmap);
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

    private void saveInformation() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("information_shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nomeProfilo", nomeProfilo);
        editor.apply();
    }



}