package com.example.footbapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import java.util.Objects;

/**
 * Activity class for the Event List Activity
 */
public class EventListActivity extends AppCompatActivity {
    private final String SUBTITLE_ACTIVITY = "Upcoming Games";
    private final String NO_EVENTS_TOAST_MESSAGE = "No events available for ";
    private final String SUBSCRIBED_EVENT_TOAST_MESSAGE = "Subscribed to ";
    private final String UNSUBSCRIBED_EVENT_TOAST_MESSAGE = "Unsubscribed from ";
    private Team team;
    private RecyclerView eventsRv;
    private List<Event> events;
    private ApiViewModel apiViewModel;
    private EventViewModel eventViewModel;
    private ProgressBar progressBar;
    private List<Event> subscribedEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        team = Objects.requireNonNull(getIntent().getExtras()).getParcelable("team");

        setTitle(Objects.requireNonNull(team).getTeamName());
        getSupportActionBar().setSubtitle(SUBTITLE_ACTIVITY);

        events = new ArrayList<>();
        subscribedEvents = (List<Event>) getIntent().getSerializableExtra("subscribedEvents");
        eventsRv = findViewById(R.id.eventsRv);
        progressBar = findViewById(R.id.eventListProgressBar);

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        isLoaded();
        loadEvents(String.valueOf(team.getIdTeam()));

    }

    /**
     * This method checks if the data is loaded. If the data is loaded the progress bar is hidden
     */
    private void isLoaded() {
        apiViewModel.IsLoaded().observe(this, isLoaded -> {
            if (isLoaded != null) {
                if (isLoaded) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * This method retrieves a teams events by id from the viewmodel
     *
     * @param id The id of the team
     */
    private void loadEvents(String id) {
        apiViewModel.getEventResource().observe(this, eventResource -> {
            events.clear();
            if (Objects.requireNonNull(eventResource).getEvents() != null) {
                events = eventResource.getEvents();
            } else {
                Toast.makeText(EventListActivity.this,
                        NO_EVENTS_TOAST_MESSAGE + team.getTeamName() + "!",
                        Toast.LENGTH_SHORT).show();
            }
            populateRecyclerView();
        });
        apiViewModel.getEventsById(id);
    }

    /**
     * This method fills the recyclerview
     *
     */
    private void populateRecyclerView() {
        EventAdapter eventAdapter = new EventAdapter(events, new EventAdapter.CheckBoxClickListener() {
            @Override
            public void onItemCheck(Event event) {
                event.setIdTeam(team.getIdTeam());
                eventViewModel.insert(event);
                event.setSubscribed(true);
                Toast.makeText(EventListActivity.this,
                        SUBSCRIBED_EVENT_TOAST_MESSAGE + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemUnCheck(Event event) {
                eventViewModel.delete(event);
                Toast.makeText(EventListActivity.this,
                        UNSUBSCRIBED_EVENT_TOAST_MESSAGE + event.getStrHomeTeam() + " - " + event.getStrAwayTeam() + "!",
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
