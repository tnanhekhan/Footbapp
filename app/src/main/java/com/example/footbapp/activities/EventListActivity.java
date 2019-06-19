package com.example.footbapp.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        loadEvents(String.valueOf(team.getIdTeam()));

    }

    private void loadEvents(String id) {
        apiViewModel.getEventResource().observe(this, eventResource -> {
            events.clear();
            if (eventResource.getEvents() != null) {
                events = eventResource.getEvents();
                Log.e("SIZE", String.valueOf(events.get(0).getStrHomeTeam()));
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
            public void onItemClick(Event event) {
                System.out.println("BOGO");
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
