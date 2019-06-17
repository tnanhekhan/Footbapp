package com.example.footbapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.footbapp.adapter.TeamAdapter;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.CompetitionListViewModel;

import java.util.ArrayList;
import java.util.List;


public class TeamListActivity extends AppCompatActivity {
    private List<Team> teams;
    private RecyclerView teamsRv;
    private TeamAdapter teamAdapter;
    private CompetitionListViewModel viewModel;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");


        teamsRv = findViewById(R.id.teamsRv);

        teams = new ArrayList<>();
        setTitle(bundle.getString("name"));

        teamAdapter = new TeamAdapter(teams);
        teamsRv.setAdapter(teamAdapter);
        teamsRv.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(CompetitionListViewModel.class);

        loadTeams(String.valueOf(id));
    }

    private void loadTeams(String id) {
        viewModel.getTeamResource().observe(this, teamResource -> {
            teams.clear();
            teams = teamResource.getTeams();
            populateRecyclerView();
        });
        viewModel.getCompetitionById(id);
    }

    public void populateRecyclerView() {
        teamAdapter = new TeamAdapter(teams);
        teamsRv.setAdapter(teamAdapter);
        teamsRv.setLayoutManager(new LinearLayoutManager(this));

        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team) {
                Intent intent = new Intent(TeamListActivity.this, TeamOverviewActivity.class);
                intent.putExtra("team", team);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

