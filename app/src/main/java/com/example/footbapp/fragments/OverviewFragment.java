package com.example.footbapp.fragments;

import android.app.Notification;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footbapp.R;
import com.example.footbapp.activities.EventListActivity;
import com.example.footbapp.activities.MainActivity;
import com.example.footbapp.adapter.EventAdapter;
import com.example.footbapp.adapter.FavoriteTeamAdapter;
import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.EventViewModel;
import com.example.footbapp.viewmodel.TeamViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.footbapp.Footbapp.CHANNEL_1_ID;


/**
 * Fragment class for the Overview Fragment
 *
 */
public class OverviewFragment extends Fragment {
    private static final String UNSUBSCRIBED_EVENT_SNACKBAR_MESSAGE = "Unsubscribed from ";
    private static final String NOTIFICATION_BODY_MESSAGE = "Starting at ";
    private TeamViewModel teamViewModel;
    private EventViewModel eventViewModel;
    private RecyclerView favoriteTeamsRv;
    private RecyclerView subscribedEventsRv;
    private FavoriteTeamAdapter favoriteTeamAdapter;
    private EventAdapter eventAdapter;
    private List<Event> subscribedEvents;
    private NotificationManagerCompat notificationManagerCompat;


    public OverviewFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        favoriteTeamsRv = Objects.requireNonNull(getActivity()).findViewById(R.id.favoriteTeamsRv);
        subscribedEventsRv = getActivity().findViewById(R.id.subscribedEventsRv);
        subscribedEventsRv.setVisibility(View.INVISIBLE);

        notificationManagerCompat = NotificationManagerCompat.from(getActivity());
        TabLayout favoriteTeamTabLayout = getActivity().findViewById(R.id.favoriteTeamTabLayout);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        loadFavoriteTeams();
        loadSubscribedEvents();

        Intent intent = getActivity().getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("code")) {
                Objects.requireNonNull(favoriteTeamTabLayout.getTabAt(1)).select();
                favoriteTeamsRv.setVisibility(View.INVISIBLE);
                subscribedEventsRv.setVisibility(View.VISIBLE);
                intent.removeExtra("code");
            }
        }

        favoriteTeamTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        favoriteTeamsRv.setVisibility(View.VISIBLE);
                        subscribedEventsRv.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        favoriteTeamsRv.setVisibility(View.INVISIBLE);
                        subscribedEventsRv.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void loadFavoriteTeams() {
        teamViewModel.getAllFavoriteTeams().observe(this, teams -> {
            favoriteTeamAdapter = new FavoriteTeamAdapter(teams);
            populateTeamRecyclerView();

        });
    }

    private void loadSubscribedEvents() {
        eventViewModel.getAllSubscribedEvents().observe(this, events -> {
            subscribedEvents = new ArrayList<>(Objects.requireNonNull(events));

            eventAdapter = new EventAdapter(events, new EventAdapter.CheckBoxClickListener() {
                @Override
                public void onItemCheck(Event event) {

                }

                @Override
                public void onItemUnCheck(Event event) {
                    final Event deletedEvent = event;
                    String deletedEventName = deletedEvent.getStrEvent();
                    eventViewModel.delete(event);
                    Snackbar.make(favoriteTeamsRv, UNSUBSCRIBED_EVENT_SNACKBAR_MESSAGE + deletedEventName + "!", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", v -> eventViewModel.insert(deletedEvent)).setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)).show();
                }
            });

            eventAdapter.setSubscribedEvents(subscribedEvents);
            sendOnChannel1(subscribedEvents);
            populateEventRecyclerView();
        });
    }

    private void populateEventRecyclerView() {
        subscribedEventsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        subscribedEventsRv.setAdapter(eventAdapter);
        subscribedEventsRv.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Event deletedEvent = eventAdapter.getEventAt(viewHolder.getAdapterPosition());
                String deletedEventName = deletedEvent.getStrEvent();
                eventViewModel.delete(eventAdapter.getEventAt(viewHolder.getAdapterPosition()));
                Snackbar.make(favoriteTeamsRv, UNSUBSCRIBED_EVENT_SNACKBAR_MESSAGE + deletedEventName + "!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> eventViewModel.insert(deletedEvent)).setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)).show();

            }
        }).attachToRecyclerView(subscribedEventsRv);
    }

    private void populateTeamRecyclerView() {
        favoriteTeamsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        favoriteTeamsRv.setAdapter(favoriteTeamAdapter);
        favoriteTeamsRv.setHasFixedSize(true);

        favoriteTeamAdapter.setOnItemClickListener(team -> {
            Intent intent = new Intent(getActivity(), EventListActivity.class);
            intent.putExtra("team", team);
            intent.putExtra("subscribedEvents", (Serializable) subscribedEvents);

            startActivity(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Team deletedTeam = favoriteTeamAdapter.getTeamAt(viewHolder.getAdapterPosition());
                String deletedTeamName = deletedTeam.getTeamName();
                teamViewModel.delete(favoriteTeamAdapter.getTeamAt(viewHolder.getAdapterPosition()));
                Snackbar.make(favoriteTeamsRv, "Removed " + deletedTeamName + " from favorite teams!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", v -> teamViewModel.insert(deletedTeam)).setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)).show();

            }
        }).attachToRecyclerView(favoriteTeamsRv);
    }

    private void sendOnChannel1(List<Event> list) {
        Intent activityIntent = new Intent(getActivity(), MainActivity.class);
        activityIntent.putExtra("code", "notified");
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(!list.isEmpty()){
            Notification notification = new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_soccer_ball)
                    .setContentTitle(list.get(0).getStrEvent())
                    .setContentText(NOTIFICATION_BODY_MESSAGE + list.get(0).getDateEvent() + " " + list.get(0).getStrTime())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_EVENT)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    .build();

            notificationManagerCompat.notify(list.get(0).getIdEvent(), notification);
        }
    }

}
