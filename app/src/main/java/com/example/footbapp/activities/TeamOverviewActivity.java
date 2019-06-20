package com.example.footbapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.footbapp.R;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.TeamViewModel;

import java.util.List;
import java.util.Objects;

/**
 * Activity class for Team Overview activity
 *
 */
public class TeamOverviewActivity extends AppCompatActivity {
    private final String DELETED_TEAM_TOAST_MESSAGE = "deleted from favorite teams!";
    private final String INSERTED_TEAM_TOAST_MESSAGE = "stored as a favorite team!";
    private Team team;
    private ImageView stadiumImageView;
    private TeamViewModel teamViewModel;
    private FloatingActionButton favoriteButton;
    private boolean favorited = false;
    private CircularProgressDrawable progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_overview);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        team = Objects.requireNonNull(getIntent().getExtras()).getParcelable("team");
        initializeComponents();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (stadiumImageView.getDrawable() == null) {
                stadiumImageView.setVisibility(View.GONE);
            }
        }, 500);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        teamViewModel.getAllFavoriteTeams().observe(this, teams -> checkDatabase(Objects.requireNonNull(teams)));

        progressBar = new CircularProgressDrawable(this);
        progressBar.setStrokeWidth(5f);
        progressBar.setCenterRadius(30f);
        progressBar.setBackgroundColor(R.color.colorPrimary);
    }

    @Override
    public boolean onSupportNavigateUp() {
        supportFinishAfterTransition();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    private void checkDatabase(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            if (Objects.equals(teams.get(i).getIdTeam(), team.getIdTeam())) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                favorited = true;
            }
        }
    }

    private void initializeComponents() {
        setTitle(team.getTeamName());

        ImageView badgeImageView = findViewById(R.id.badgeImageView);
        ImageView kitImageView = findViewById(R.id.kitImageView);
        stadiumImageView = findViewById(R.id.stadiumImageView);
        TextView stadiumTextView = findViewById(R.id.stadiumTextView);
        TextView locationTextView = findViewById(R.id.locationTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        favoriteButton = findViewById(R.id.favoriteFAB);

        badgeImageView.setTransitionName(team.getTeamName());

        stadiumTextView.setText(getResources().getString(R.string.stadium_name, team.getStadiumName()));
        locationTextView.setText(getResources().getString(R.string.location, team.getLocation()));
        descriptionTextView.setText(team.getDescription());


        Glide.with(this).load(team.getBadgePath()).placeholder(progressBar).into(badgeImageView);
        Glide.with(this).load(team.getKitImage()).placeholder(progressBar).into(kitImageView);
        Glide.with(this).load(team.getStadiumImage()).placeholder(progressBar).into(stadiumImageView);

        favoriteButton.setOnClickListener(v -> {
            if (favorited) {
                teamViewModel.delete(team);
                Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + DELETED_TEAM_TOAST_MESSAGE, Toast.LENGTH_SHORT).show();
                favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            } else {
                teamViewModel.insert(team);
                Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + INSERTED_TEAM_TOAST_MESSAGE, Toast.LENGTH_SHORT).show();
                favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        });
    }
}
