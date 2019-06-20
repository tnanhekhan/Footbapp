package com.example.footbapp.fragments;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.footbapp.R;
import com.example.footbapp.activities.MainActivity;
import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.EventViewModel;
import com.example.footbapp.viewmodel.TeamViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.footbapp.Footbapp.CHANNEL_1_ID;


public class SettingsFragment extends Fragment {
    private Button deleteEventsButton;
    private Button clearFavoriteTeamButton;
    private Button testNotifcationButton;
    private TeamViewModel teamViewModel;
    private EventViewModel eventViewModel;
    private NotificationManagerCompat notificationManagerCompat;


    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        deleteEventsButton = getActivity().findViewById(R.id.deleteEventsButton);
        clearFavoriteTeamButton = getActivity().findViewById(R.id.clearFavoriteTeamButton);
        testNotifcationButton = getActivity().findViewById(R.id.testNotificationButton);

        notificationManagerCompat = NotificationManagerCompat.from(getActivity());

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        setOnClickButtons();

    }

    private void setOnClickButtons() {
        clearFavoriteTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Team> favoriteTeamsBackup = new ArrayList<>();

                teamViewModel.getAllFavoriteTeams().observe(SettingsFragment.this, new Observer<List<Team>>() {
                    @Override
                    public void onChanged(@Nullable List<Team> teams) {
                        for (int i = 0; i < teams.size(); i++) {
                            favoriteTeamsBackup.add(i, teams.get(i));
                        }
                    }
                });

                teamViewModel.deleteAllFavoriteTeams();

                Snackbar.make(v, "Cleared all favorite teams!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < favoriteTeamsBackup.size(); i++) {
                                    teamViewModel.insert(favoriteTeamsBackup.get(i));
                                }
                            }
                        }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)).show();
            }

        });

        deleteEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Event> subscribedEventsBackup = new ArrayList<>();

                eventViewModel.getAllSubscribedEvents().observe(SettingsFragment.this, new Observer<List<Event>>() {
                    @Override
                    public void onChanged(@Nullable List<Event> events) {
                        for (int i = 0; i < events.size(); i++) {
                            subscribedEventsBackup.add(i, events.get(i));

                        }
                    }
                });

                eventViewModel.deletAllSubscribedEvents();

                Snackbar.make(v, "Cleared all subscribed events!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < subscribedEventsBackup.size(); i++) {
                                    eventViewModel.insert(subscribedEventsBackup.get(i));
                                }
                            }
                        }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)).show();
            }
        });

        testNotifcationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("woop");
                sendOnChannel1(v);
            }
        });
    }

    public void sendOnChannel1(View v) {
        Intent activityIntent = new Intent(getActivity(), MainActivity.class);
        activityIntent.putExtra("code", "notified");
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_soccer_ball)
                .setContentTitle("Test title")
                .setContentText("Test message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    private void scheduleAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
    }
}
