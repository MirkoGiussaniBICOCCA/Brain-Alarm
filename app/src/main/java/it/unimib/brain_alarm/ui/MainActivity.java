package it.unimib.brain_alarm.ui;

import static it.unimib.brain_alarm.CarrieraFragment.EXTRA_BUNDLE_INT;
import static it.unimib.brain_alarm.CarrieraFragment.EXTRA_BUTTON_PRESSED_COUNTER_KEY;
import static it.unimib.brain_alarm.CarrieraFragment.EXTRA_NEWS_KEY;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

import it.unimib.brain_alarm.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String COUNTRY_SAVED = "country";
    private static String stato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_menu_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.impostazioniFragment,
                R.id.suoniFragment,
                R.id.homeFragment,
                R.id.riposoFragment,
                R.id.carrieraFragment).build();

        NavigationUI.setupWithNavController(bottomNav, navController);

        Intent intent = getIntent();
        Log.d(TAG, "Times: " + intent.getIntExtra(EXTRA_BUTTON_PRESSED_COUNTER_KEY, 0));
        Log.d(TAG, "News: " + intent.getParcelableExtra(EXTRA_NEWS_KEY));

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.d(TAG, "Int from Bundle " + bundle.getInt(EXTRA_BUNDLE_INT));
        }
    }


    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString(COUNTRY_SAVED, stato);
    }
    */
}
