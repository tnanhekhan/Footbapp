package com.example.footbapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.footbapp.R;
import com.example.footbapp.adapter.FavoriteTeamAdapter;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;
import com.example.footbapp.viewmodel.RoomViewModel;

import java.util.List;


public class FavoriteTeamFragment extends Fragment {
    private RoomViewModel roomViewModel;
    private ApiViewModel apiViewModel;
    private RecyclerView favoriteTeamsRv;
    private RecyclerView eventRv;
    private FavoriteTeamAdapter favoriteTeamAdapter;


    public FavoriteTeamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_team, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        favoriteTeamsRv = getActivity().findViewById(R.id.favoriteTeamsRv);
        favoriteTeamsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteTeamsRv.setHasFixedSize(true);

        favoriteTeamAdapter = new FavoriteTeamAdapter();
        favoriteTeamsRv.setAdapter(favoriteTeamAdapter);

        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        roomViewModel.getAllFavoriteTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                favoriteTeamAdapter.setFavoriteTeams(teams);
            }
        });

    }
}
