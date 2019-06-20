package com.example.footbapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.footbapp.R;
import com.example.footbapp.adapter.EventAdapter;
import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;
import com.example.footbapp.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {
    private Team team;
    private RecyclerView eventsRv;
    private EventAdapter eventAdapter;
    private List<Event> events;
    private ApiViewModel apiViewModel;
    private EventViewModel eventViewModel;
    private ProgressBar progressBar;
    private List<Event> subscribedEvents;
    private boolean subscribed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        team = getIntent().getExtras().getParcelable("team");

        setTitle(team.getTeamName());
        getSupportActionBar().setSubtitle("Upcoming Games");

        events = new ArrayList<>();
        subscribedEvents = (List<Event>) getIntent().getSerializableExtra("subscribedEvents");
        eventsRv = findViewById(R.id.eventsRv);
        progressBar = findViewById(R.id.eventListProgressBar);

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        isLoaded();
        loadEvents(String.valueOf(team.getIdTeam()));

        Log.e("SubscribedSize", String.valueOf(subscribedEvents.size()));
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
        eventAdapter = new EventAdapter(events, new EventAdapter.CheckBoxClickListener() {
            @Override
            public void onItemCheck(Event event, CheckBox notificationCheckBox) {
                eventViewModel.insert(event);
                event.setSubscribed(true);
                Toast.makeText(EventListActivity.this,
                        "Subscribed to " + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemUnCheck(Event event, CheckBox notificationCheckBox) {
                eventViewModel.delete(event);
                Toast.makeText(EventListActivity.this,
                        "Unsubscribed from " + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        eventAdapter.setSubscribedEvents(subscribedEvents);
        eventsRv.setLayoutManager(new GridLayoutManager(this, 1));
        eventsRv.setAdapter(eventAdapter);

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
