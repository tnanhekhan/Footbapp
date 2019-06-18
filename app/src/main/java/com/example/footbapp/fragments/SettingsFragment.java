package com.example.footbapp.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.footbapp.R;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;


public class SettingsFragment extends Fragment {
    private Button deleteEventsButton;
    private Button clearFavoriteTeamButton;
    private RoomViewModel roomViewModel;


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

        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);

        clearFavoriteTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Team> favoriteTeamsBackup = new ArrayList<>();

                roomViewModel.getAllFavoriteTeams().observe(SettingsFragment.this, new Observer<List<Team>>() {
                    @Override
                    public void onChanged(@Nullable List<Team> teams) {
                        for (int i = 0; i < teams.size(); i++) {
                            favoriteTeamsBackup.add(i, teams.get(i));
                        }
                    }
                });

                roomViewModel.deleteAllFavoriteTeams();

                Snackbar.make(v, "Cleared all favorite teams!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < favoriteTeamsBackup.size(); i++) {
                                    roomViewModel.insert(favoriteTeamsBackup.get(i));
                                }
                            }
                        }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)).show();
            }

        });

    }
}
