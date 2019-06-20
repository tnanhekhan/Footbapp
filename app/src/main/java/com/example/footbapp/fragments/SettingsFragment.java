package com.example.footbapp.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.footbapp.R;
import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.EventViewModel;
import com.example.footbapp.viewmodel.TeamViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Fragment Class for the Settings Fragment
 *
 */
public class SettingsFragment extends Fragment {
    private Button deleteEventsButton;
    private Button clearFavoriteTeamButton;
    private TeamViewModel teamViewModel;
    private EventViewModel eventViewModel;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        deleteEventsButton = Objects.requireNonNull(getActivity()).findViewById(R.id.deleteEventsButton);
        clearFavoriteTeamButton = getActivity().findViewById(R.id.clearFavoriteTeamButton);

        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        setOnClickButtons();

    }

    private void setOnClickButtons() {
        clearFavoriteTeamButton.setOnClickListener(v -> {
            final List<Team> favoriteTeamsBackup = new ArrayList<>();

            teamViewModel.getAllFavoriteTeams().observe(SettingsFragment.this, teams -> {
                for (int i = 0; i < Objects.requireNonNull(teams).size(); i++) {
                    favoriteTeamsBackup.add(i, teams.get(i));
                }
            });

            teamViewModel.deleteAllFavoriteTeams();

            Snackbar.make(v, getString(R.string.cleared_all_favorite_teams), Snackbar.LENGTH_LONG)
                    .setAction("UNDO", v12 -> {
                        for (int i = 0; i < favoriteTeamsBackup.size(); i++) {
                            teamViewModel.insert(favoriteTeamsBackup.get(i));
                        }
                    }).setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)).show();
        });

        deleteEventsButton.setOnClickListener(v -> {
            final List<Event> subscribedEventsBackup = new ArrayList<>();

            eventViewModel.getAllSubscribedEvents().observe(SettingsFragment.this, events -> {
                for (int i = 0; i < Objects.requireNonNull(events).size(); i++) {
                    subscribedEventsBackup.add(i, events.get(i));

                }
            });

            eventViewModel.deletAllSubscribedEvents();

            Snackbar.make(v, getString(R.string.cleared_all_subscribed_events), Snackbar.LENGTH_LONG)
                    .setAction("UNDO", v1 -> {
                        for (int i = 0; i < subscribedEventsBackup.size(); i++) {
                            eventViewModel.insert(subscribedEventsBackup.get(i));
                        }
                    }).setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)).show();
        });
    }
}
