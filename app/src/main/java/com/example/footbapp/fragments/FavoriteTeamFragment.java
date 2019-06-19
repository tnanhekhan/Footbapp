package com.example.footbapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.footbapp.R;
import com.example.footbapp.activities.EventListActivity;
import com.example.footbapp.adapter.FavoriteTeamAdapter;
import com.example.footbapp.model.Team;
import com.example.footbapp.viewmodel.ApiViewModel;
import com.example.footbapp.viewmodel.RoomViewModel;

import java.util.List;


public class FavoriteTeamFragment extends Fragment {
    private RoomViewModel roomViewModel;
    private ApiViewModel apiViewModel;
    private RecyclerView favoriteTeamsRv;
    private RecyclerView subscribedEventsRv;
    private FavoriteTeamAdapter favoriteTeamAdapter;
    private TabLayout favoriteTeamTabLayout;
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
        subscribedEventsRv = getActivity().findViewById(R.id.subscribedEventsRv);


        favoriteTeamTabLayout = getActivity().findViewById(R.id.favoriteTeamTabLayout);

        roomViewModel = ViewModelProviders.of(this).get(RoomViewModel.class);
        loadFavoriteTeams();

        favoriteTeamTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        favoriteTeamsRv.setVisibility(View.VISIBLE);
                        System.out.println("FAVORITE TEAMS");
                        break;
                    case 1:
                        favoriteTeamsRv.setVisibility(View.INVISIBLE);
                        System.out.println("SUBSCRIBED EVENTS");
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
        roomViewModel.getAllFavoriteTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                favoriteTeamAdapter = new FavoriteTeamAdapter(teams);
                populateRecyclerView();

            }
        });
    }

    private void populateRecyclerView() {
        favoriteTeamsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        favoriteTeamsRv.setAdapter(favoriteTeamAdapter);
        favoriteTeamsRv.setHasFixedSize(true);

        favoriteTeamAdapter.setOnItemClickListener(new FavoriteTeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Team team, String teamName, ImageView imageView) {
                Intent intent = new Intent(getActivity(), EventListActivity.class);
                intent.putExtra("team", team);

                startActivity(intent);
            }
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
                roomViewModel.delete(favoriteTeamAdapter.getTeamAt(viewHolder.getAdapterPosition()));
                Snackbar.make(favoriteTeamsRv, "Removed " + deletedTeamName + " from favorite teams!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                roomViewModel.insert(deletedTeam);
                            }
                        }).setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)).show();

            }
        }).attachToRecyclerView(favoriteTeamsRv);
    }

}
