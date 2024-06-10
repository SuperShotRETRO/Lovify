package com.example.lovify.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;


import com.example.lovify.R;
import com.example.lovify.utils.ProfileCardAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    Fragment homeFragment, matchedFragment, profileFragment;
    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);



        homeFragment = new Home();
        matchedFragment = new Matched();
        profileFragment = new Profile();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragframe,homeFragment).commit();

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.bottom_nav_homepage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, homeFragment).commit();
                    return true;
                } else if (id == R.id.bottom_nav_matched) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, matchedFragment).commit();
                    return true;
                } else if (id == R.id.bottom_nav_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragframe, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }
}