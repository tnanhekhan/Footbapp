package com.example.footbapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.footbapp.R;
import com.example.footbapp.adapter.EventAdapter;
import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {
    private Team team;
    private RecyclerView eventsRv;
    private EventAdapter eventAdapter;
    private List<Event> events;
    private ApiViewModel apiViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        team = getIntent().getExtras().getParcelable("team");

        setTitle(team.getTeamName());
        getSupportActionBar().setSubtitle("Upcoming Games");

        events = new ArrayList<>();
        eventsRv = findViewById(R.id.eventsRv);
        progressBar = findViewById(R.id.eventListProgressBar);

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);

        isLoaded();
        loadEvents(String.valueOf(team.getIdTeam()));
    }

    private void isLoaded() {
        apiViewModel.IsLoaded().observe(this, new Observer<Boolean>() {
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

    private void loadEvents(String id) {
        apiViewModel.getEventResource().observe(this, eventResource -> {
            events.clear();
            if (eventResource.getEvents() != null) {
                events = eventResource.getEvents();
            } else {
                Toast.makeText(EventListActivity.this,
                        "No events available for " + team.getTeamName() + "!",
                        Toast.LENGTH_SHORT).show();
            }
            populateRecyclerView();
        });
        apiViewModel.getEventsById(id);
    }

    private void populateRecyclerView() {
        eventAdapter = new EventAdapter(events);
        eventsRv.setLayoutManager(new GridLayoutManager(this, 1));
        eventsRv.setAdapter(eventAdapter);

        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event, ImageView notificationIcon) {
                if (event.getStrDate() != null) {
                    //TO DO: IMPLEMENT ONCLICK NOTIFCATION
                    if(notificationIcon.getDrawable() == getDrawable(R.drawable.ic_notifications_off_black_24dp)){
                        notificationIcon.setImageResource(R.drawable.ic_notifications_black_24dp);
                        Toast.makeText(EventListActivity.this,
                                "Subscribed to " + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
                                Toast.LENGTH_SHORT).show();
                    }else if(notificationIcon.getDrawable() == getDrawable(R.drawable.ic_notifications_black_24dp)){
                        notificationIcon.setImageResource(R.drawable.ic_notifications_off_black_24dp);
                        Toast.makeText(EventListActivity.this,
                                "Unsubscribed from " + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
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
}
