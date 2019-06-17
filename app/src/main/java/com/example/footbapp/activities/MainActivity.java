package com.example.footbapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.footbapp.R;
import com.example.footbapp.fragments.CompetitionListFragment;
import com.example.footbapp.fragments.FavoriteTeamFragment;
import com.example.footbapp.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private final Fragment CompetitiveListFragment = new CompetitionListFragment();
    private final Fragment FavoriteTeamFragment = new FavoriteTeamFragment();
    private final Fragment SettingsFragment = new SettingsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment active = CompetitiveListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().hide(active).show(CompetitiveListFragment).commit();
                    active = CompetitiveListFragment;
                    return true;
                case R.id.navigation_favorite_teams:
                    fragmentManager.beginTransaction().hide(active).show(FavoriteTeamFragment).commit();
                    active = FavoriteTeamFragment;
                    return true;
                case R.id.navigation_settings:
                    fragmentManager.beginTransaction().hide(active).show(SettingsFragment).commit();
                    active = SettingsFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, SettingsFragment, "3").hide(SettingsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, FavoriteTeamFragment, "2").hide(FavoriteTeamFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, CompetitiveListFragment, "1").commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
