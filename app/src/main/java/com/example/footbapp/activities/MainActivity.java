package com.example.footbapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.footbapp.R;
import com.example.footbapp.fragments.CompetitionListFragment;
import com.example.footbapp.fragments.OverviewFragment;
import com.example.footbapp.fragments.SettingsFragment;

/**
 * Activity class for the Main Activity where all fragments are contained
 *
 */
public class MainActivity extends AppCompatActivity {
    private final String INTENT_NOTIFICATION_CODE = "code";
    private final String THIRD_FRAGMENT_ID = "3";
    private final String SECOND_FRAGMENT_ID = "2";
    private final String FIRST_FRAGMENT_ID = "1";
    private final Fragment competitionListFragment = new CompetitionListFragment();
    private final Fragment favoriteTeamFragment = new OverviewFragment();
    private final Fragment settingsFragment = new SettingsFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment active = competitionListFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_competition:
                        fragmentManager.beginTransaction().hide(active).show(competitionListFragment).commit();
                        active = competitionListFragment;
                        return true;
                    case R.id.navigation_overview:
                        fragmentManager.beginTransaction().hide(active).show(favoriteTeamFragment).commit();
                        active = favoriteTeamFragment;
                        return true;
                    case R.id.navigation_settings:
                        fragmentManager.beginTransaction().hide(active).show(settingsFragment).commit();
                        active = settingsFragment;
                        return true;
                }
                return false;
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
            if (extras.containsKey(INTENT_NOTIFICATION_CODE)) {
                active = favoriteTeamFragment;
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, settingsFragment, THIRD_FRAGMENT_ID).hide(settingsFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, competitionListFragment, SECOND_FRAGMENT_ID).hide(competitionListFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, favoriteTeamFragment, FIRST_FRAGMENT_ID).commit();
                navView.setSelectedItemId(R.id.navigation_overview);

            }
        } else {
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, settingsFragment, THIRD_FRAGMENT_ID).hide(settingsFragment).commit();
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, favoriteTeamFragment, SECOND_FRAGMENT_ID).hide(favoriteTeamFragment).commit();
            fragmentManager.beginTransaction().add(R.id.fragmentHolderLayout, competitionListFragment, FIRST_FRAGMENT_ID).commit();

        }
    }
}
