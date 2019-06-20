package com.example.footbapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.footbapp.R;
import com.example.footbapp.adapter.TeamAdapter;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Activity class for Team List activity
 *
 */
public class TeamListActivity extends AppCompatActivity {
    public final String NO_EVENTS_TOAST_MESSAGE = "No events available for ";
    private List<Team> teams;
    private RecyclerView teamsRv;
    private TeamAdapter teamAdapter;
    private ApiViewModel viewModel;
    private ProgressBar progressBar;
    private String competitionName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int id = Objects.requireNonNull(bundle).getInt("id");

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
        viewModel.IsLoaded().observe(this, isLoaded -> {
            if (isLoaded != null) {
                if (isLoaded) {
                    progressBar.setVisibility(View.GONE);
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
                        NO_EVENTS_TOAST_MESSAGE + competitionName + "!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getCompetitionById(id);
    }

    private void populateRecyclerView() {
        teamAdapter = new TeamAdapter(teams);
        teamsRv.setLayoutManager(new GridLayoutManager(this, 1));
        teamsRv.setAdapter(teamAdapter);

        teamAdapter.setOnItemClickListener((team, teamName, teamImageView) -> {
            Intent intent = new Intent(TeamListActivity.this, TeamOverviewActivity.class);
            intent.putExtra("team", team);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            TeamListActivity.this,
                            teamImageView,
                            teamName);

            startActivity(intent, optionsCompat.toBundle());
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                teamAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

