package com.example.footbapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.TeamViewModel;

import java.util.List;

public class TeamOverviewActivity extends AppCompatActivity {
    private Team team;
    private ImageView badgeImageView;
    private ImageView kitImageView;
    private ImageView stadiumImageView;
    private TextView stadiumTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;
    private TeamViewModel teamViewModel;
    private FloatingActionButton favoriteButton;
    private boolean favorited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        team = getIntent().getExtras().getParcelable("team");
        initializeComponents();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (stadiumImageView.getDrawable() == null) {
                    stadiumImageView.setVisibility(View.GONE);
                }
            }
        }, 500);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.getAllFavoriteTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                checkDatabase(teams);
                Toast.makeText(TeamOverviewActivity.this, "Data changed - Number of teams in db: " + teamViewModel.getAllFavoriteTeams().getValue().size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void checkDatabase(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            if (teams.get(i).getIdTeam() == team.getIdTeam()) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                favorited = true;
            }
        }
    }

    private void initializeComponents() {
        setTitle(team.getTeamName());

        badgeImageView = findViewById(R.id.badgeImageView);
        kitImageView = findViewById(R.id.kitImageView);
        stadiumImageView = findViewById(R.id.stadiumImageView);
        stadiumTextView = findViewById(R.id.stadiumTextView);
        locationTextView = findViewById(R.id.locationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        favoriteButton = findViewById(R.id.favoriteFAB);

        stadiumTextView.setText("Stadium name: " + team.getStadiumName());
        locationTextView.setText("Location: " + team.getLocation());
        descriptionTextView.setText(team.getDescription());


        Glide.with(this).load(team.getBadgePath()).into(badgeImageView);
        Glide.with(this).load(team.getKitImage()).into(kitImageView);
        Glide.with(this).load(team.getStadiumImage()).into(stadiumImageView);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorited) {
                    teamViewModel.delete(team);
                    Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + " deleted from favorite teams!", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    teamViewModel.insert(team);
                    Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + " stored as favorite team!", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
    }
}
