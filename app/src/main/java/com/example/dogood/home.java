package com.example.dogood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorBlue));
        window.setNavigationBarColor(this.getResources().getColor(R.color.colorBlue));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_home);

//        Bottom navigation view
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        Set home fragment main
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout,new navHomeFragment()).commit();

    }

//    Double tap back button to close app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double Tap To Exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

//    Lister nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
        BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigationHome:
                        selectedFragment = new navHomeFragment();
                        break;

                    case R.id.navigationQr:
                        selectedFragment = new navQrFragment();
                        break;

                    case R.id.navigationProfile:
                        selectedFragment = new navProfileFragment();
                        break;

                    case R.id.navigationLogout:
                        selectedFragment = new navLogoutFragment();
                        break;

                    default:
                        break;

                }

//                Begin Transition
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, selectedFragment).commit();

                return true;

            }
        };

}