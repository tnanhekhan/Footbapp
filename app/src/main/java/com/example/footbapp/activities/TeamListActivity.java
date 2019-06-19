package com.example.footbapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.footbapp.R;
import com.example.footbapp.adapter.TeamAdapter;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;

import java.util.ArrayList;
import java.util.List;


public class TeamListActivity extends AppCompatActivity {
    private List<Team> teams;
    private RecyclerView teamsRv;
    private TeamAdapter teamAdapter;
    private ApiViewModel viewModel;
    private int id;
    private ProgressBar progressBar;
    private String competitionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        teamsRv = findViewById(R.id.teamsRv);
        progressBar = findViewById(R.id.teamListProgressBar);

        teams = new ArrayList<>();
        competitionName = bundle.getString("name");
        setTitle(competitionName);

        viewModel = ViewModelProviders.of(this).get(ApiViewModel.class);

        isLoaded();
        loadTeams(String.valueOf(id));
    }

    private void isLoaded() {
        viewModel.IsLoaded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoaded) {
                if (isLoaded != null) {
                    if (isLoaded) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void loadTeams(String id) {
        viewModel.getTeamResource().observe(this, teamResource -> {
            teams.clear();
            if (teamResource != null) {
                teams = teamResource.getTeams();
                populateRecyclerView();
            } else {
                Toast.makeText(TeamListActivity.this,
                        "No events available for " + competitionName + "!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCompetitionById(id);
    }

    private void populateRecyclerView() {
        teamAdapter = new TeamAdapter(teams);
        teamsRv.setLayoutManager(new GridLayoutManager(this, 1));
        teamsRv.setAdapter(teamAdapter);

        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team, String teamName, ImageView teamImageView) {
                Intent intent = new Intent(TeamListActivity.this, TeamOverviewActivity.class);
                intent.putExtra("team", team);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(
                                TeamListActivity.this,
                                teamImageView,
                                teamName);

                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

