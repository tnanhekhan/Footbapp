package com.example.footbapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.footbapp.model.Team;

public class TeamOverviewActivity extends AppCompatActivity {
    private Team team;
    private ImageView badgeImageView;
    private ImageView kitImageView;
    private ImageView stadiumImageView;
    private TextView stadiumTextView;
    private TextView locationTextView;
    private TextView descriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_overview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        team = getIntent().getExtras().getParcelable("team");
        setTitle(team.getTeamName());

        badgeImageView = findViewById(R.id.badgeImageView);
        kitImageView = findViewById(R.id.kitImageView);
        stadiumImageView = findViewById(R.id.stadiumImageView);
        stadiumTextView = findViewById(R.id.stadiumTextView);
        locationTextView = findViewById(R.id.locationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        stadiumTextView.setText("Stadium name: " + team.getStadiumName());
        locationTextView.setText("Location: " + team.getLocation());
        descriptionTextView.setText(team.getDescription());


        Glide.with(this).load(team.getBadgePath()).into(badgeImageView);
        Glide.with(this).load(team.getKitImage()).into(kitImageView);
        Glide.with(this).load(team.getStadiumImage()).into(stadiumImageView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (stadiumImageView.getDrawable() == null) {
                    stadiumImageView.setVisibility(View.GONE);
                }
            }
        }, 500);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.team_overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.playerButton) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
