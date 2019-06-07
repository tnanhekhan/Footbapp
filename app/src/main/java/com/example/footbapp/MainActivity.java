package com.example.footbapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.footbapp.adapter.CompetitionAdapter;
import com.example.footbapp.model.Competition;
import com.example.footbapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private MainActivityViewModel viewModel;
    private List<Competition> competitions;
    private RecyclerView competitionsRv;
    private CompetitionAdapter competitionAdapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        mTextMessage = findViewById(R.id.message);
        competitionsRv = findViewById(R.id.competitionsRv);

        competitions = new ArrayList<>();


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        loadCompetitions();


    }

    private void loadCompetitions() {
        viewModel.getCompetitionResource().observe(this, competitionResource -> {
            competitions.clear();
            competitions = competitionResource.getCompetitions();
            System.out.println(competitions);
            populateRecyclerView();
        });
        viewModel.getCompetitions();
    }


    public void populateRecyclerView() {
        competitionAdapter = new CompetitionAdapter(filterList());
        competitionsRv.setAdapter(competitionAdapter);
        competitionsRv.setLayoutManager(new LinearLayoutManager(this));

        competitionAdapter.setOnItemClickListener(new CompetitionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Competition competition) {
                Intent intent = new Intent(MainActivity.this, TeamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", competition.getId());
                bundle.putString("name", competition.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public List<Competition> filterList() {
        List<Competition> filteredList = new ArrayList<>();
        for (int i = 0; i < competitions.size(); i++) {
            if (competitions.get(i).getCompCheck() != 1) {
                filteredList.add(competitions.get(i));
            }
        }
        return filteredList;
    }

}
