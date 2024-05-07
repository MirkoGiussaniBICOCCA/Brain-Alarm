package it.unimib.brain_alarm;

import static android.content.Intent.getIntent;
import static java.lang.String.valueOf;
import static it.unimib.brain_alarm.util.Constants.FRANCE;
import static it.unimib.brain_alarm.util.Constants.GERMANY;
import static it.unimib.brain_alarm.util.Constants.ITALY;
import static it.unimib.brain_alarm.util.Constants.UNITED_KINGDOM;
import static it.unimib.brain_alarm.util.Constants.UNITED_STATES;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST;
import static it.unimib.brain_alarm.util.Constants.SHARED_PREFERENCES_FILE_NAME;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import java.util.GregorianCalendar;

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

import java.io.IOException;

import it.unimib.brain_alarm.R;
import it.unimib.brain_alarm.News.News;
import it.unimib.brain_alarm.News.NewsSource;
import it.unimib.brain_alarm.ui.MainActivity;
import it.unimib.brain_alarm.util.SharedPreferencesUtil;


public class CarrieraFragment extends Fragment {

    private static final String TAG = CarrieraFragment.class.getSimpleName();

    TextView textStato;
    private static final String COUNTRY_SAVED = "country";
    public static final String EXTRA_BUTTON_PRESSED_COUNTER_KEY = "BUTTON_PRESSED_COUNTER_KEY";

    public static final String EXTRA_NEWS_KEY = "NEWS_KEY";
    public static final String EXTRA_BUNDLE_INT = "BUNDLE_INT";
    private static String stato;
    private int buttonNextPressedCounter;
    private Spinner spinnerCountries;

    private News news;

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

        spinnerCountries = getActivity().findViewById(R.id.spinner_countries);


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

        final Button buttonSalva = view.findViewById(R.id.buttonCountry);

        if (savedInstanceState != null) {
            stato = savedInstanceState.getString(COUNTRY_SAVED);
            news = savedInstanceState.getParcelable(EXTRA_NEWS_KEY);
            Log.d(TAG, "stringa salvata: " + stato);
        }



        textStato = view.findViewById((R.id.textStatoSel));


        buttonSalva.setOnClickListener(v -> {

            if (isCountryOfInterestSelected()) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        getString(R.string.mxStato), Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Stato selezionato");
                saveInformation();
                textStato.setText("stato selezionato: " + spinnerCountries.getSelectedItem());
            } else
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.stato), Snackbar.LENGTH_LONG).show();

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
                // Pass in the mime type you want to let the user select
                // as the input
                mGetContent.launch("image/*");
            }
        });


    }

    private boolean isCountryOfInterestSelected() {
        spinnerCountries = getActivity().findViewById(R.id.spinner_countries);
        if (spinnerCountries.getSelectedItem() != null) {
            return true;
        } else
            return false;
    }

    private String getShortNameCountryOfInterest(String userVisibleCountryOfInterest) {
        if (userVisibleCountryOfInterest.equals(getString(R.string.francia))) {
            return FRANCE;
        } else if (userVisibleCountryOfInterest.equals(getString(R.string.germania))) {
            return GERMANY;
        } else if (userVisibleCountryOfInterest.equals(getString(R.string.italia))) {
            return ITALY;
        } else if (userVisibleCountryOfInterest.equals(getResources().getString(R.string.uk))) {
            return UNITED_KINGDOM;
        } else if (userVisibleCountryOfInterest.equals(getResources().getString(R.string.us))) {
            return UNITED_STATES;
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(EXTRA_BUTTON_PRESSED_COUNTER_KEY, buttonNextPressedCounter);
        outState.putParcelable(EXTRA_NEWS_KEY, news);
    }

    private void saveInformation() {
        String country = spinnerCountries.getSelectedItem().toString();
        String countryShortName = getShortNameCountryOfInterest(country);
        Log.d(TAG, "stato salvato: " + countryShortName);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplication());
        sharedPreferencesUtil.writeStringData(
                SHARED_PREFERENCES_FILE_NAME, SHARED_PREFERENCES_COUNTRY_OF_INTEREST, countryShortName);
    }


}