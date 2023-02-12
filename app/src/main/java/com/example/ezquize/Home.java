package com.example.ezquize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ezquize.databinding.ActivityHomeBinding;
import com.example.ezquize.fragments.Shelf;
import com.example.ezquize.fragments.Statistics;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    private ActivityHomeBinding homeBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFC947")));
        setContentView(homeBinding.getRoot());
        updateFragment(new Shelf());
        actionBar.setTitle("Home");
        homeBinding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_bar:
                        updateFragment(new Shelf());
                        actionBar.setTitle("Home");
                        break;
                    case R.id.stats_bar:
                        updateFragment(new Statistics());
                        actionBar.setTitle("Statistics");
                        break;
                }
                return true;
            }
        });
    }

    protected void updateFragment(Fragment fragment){
        FragmentManager getFragment = getSupportFragmentManager();
        FragmentTransaction transaction = getFragment.beginTransaction();
        transaction.replace(homeBinding.frameLayout.getId(), fragment);
        transaction.commit();
    }


}