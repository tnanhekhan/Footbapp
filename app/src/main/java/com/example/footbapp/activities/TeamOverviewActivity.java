package com.example.footbapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.example.footbapp.viewmodel.RoomViewModel;

import java.util.List;

public class TeamOverviewActivity extends AppCompatActivity {
    private Team team;
    private ImageView badgeImageView;
    private ImageView kitImageView;
    private ImageView stadiumImageView;
    private TextView stadiumTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;
    private RoomViewModel roomViewModel;
    private FloatingActionButton favoriteButton;
    private boolean favorited = false;
    private CircularProgressDrawable progressBar;


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

        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.getAllFavoriteTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                checkDatabase(teams);
            }
        });

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
        supportFinishAfterTransition();;
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

        badgeImageView.setTransitionName(team.getTeamName());

        stadiumTextView.setText("Stadium name: " + team.getStadiumName());
        locationTextView.setText("Location: " + team.getLocation());
        descriptionTextView.setText(team.getDescription());


        Glide.with(this).load(team.getBadgePath()).placeholder(progressBar).into(badgeImageView);
        Glide.with(this).load(team.getKitImage()).placeholder(progressBar).into(kitImageView);
        Glide.with(this).load(team.getStadiumImage()).placeholder(progressBar).into(stadiumImageView);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorited) {
                    roomViewModel.delete(team);
                    Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + " deleted from favorite teams!", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    roomViewModel.insert(team);
                    Toast.makeText(TeamOverviewActivity.this, team.getTeamName() + " stored as a favorite team!", Toast.LENGTH_SHORT).show();
                    favoriteButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        });
    }
}
