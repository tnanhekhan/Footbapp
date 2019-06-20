package com.example.footbapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.footbapp.R;
import com.example.footbapp.fragments.CompetitionListFragment;
import com.example.footbapp.fragments.FavoriteTeamFragment;
import com.example.footbapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private final Fragment competitionListFragment = new CompetitionListFragment();
    private final Fragment favoriteTeamFragment = new FavoriteTeamFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment active = competitionListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().hide(active).show(competitionListFragment).commit();
                    active = competitionListFragment;
                    return true;
                case R.id.navigation_favorite_teams:
                    fragmentManager.beginTransaction().hide(active).show(favoriteTeamFragment).commit();
                    active = favoriteTeamFragment;
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.beginTransaction().hide(active).show(settingsFragment).commit();
                    active = settingsFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("code")) {
                Log.e("Code", extras.getString("code"));
                active = favoriteTeamFragment;
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, settingsFragment, "3").hide(settingsFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, competitionListFragment, "2").hide(competitionListFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, favoriteTeamFragment, "1").commit();
                navView.setSelectedItemId(R.id.navigation_favorite_teams);

            }
        } else {
            Log.e("Code", "BUNDLE IS NULL");
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, settingsFragment, "3").hide(settingsFragment).commit();
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, favoriteTeamFragment, "2").hide(favoriteTeamFragment).commit();
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, competitionListFragment, "1").commit();

        }
    }
}
