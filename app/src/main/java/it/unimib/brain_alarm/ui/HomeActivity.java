package it.unimib.brain_alarm.ui;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import it.unimib.brain_alarm.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

    }
}

    /*private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination){
        if(USE_NAVIGATION_COMPONENT){
            Navigation.findNavController(requireView()).navigate(destination);
        }else{
            Intent intent=new Intent(requireContext(),destinationActivity);
            startActivity(intent);
        }
        requireActivity().finish();
        }*/


