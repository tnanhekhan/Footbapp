package com.example.footbapp.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.footbapp.R;
import com.example.footbapp.activities.TeamListActivity;
import com.example.footbapp.adapter.CompetitionAdapter;
import com.example.footbapp.model.Competition;
import com.example.footbapp.viewmodel.CompetitionListViewModel;

import java.util.ArrayList;
import java.util.List;


public class CompetitionListFragment extends Fragment {
    private TextView message;
    private CompetitionListViewModel viewModel;
    private List<Competition> competitions;
    private RecyclerView competitionsRv;
    private CompetitionAdapter competitionAdapter;

    public CompetitionListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        message = getActivity().findViewById(R.id.competitionListMessage);


        competitionsRv = getActivity().findViewById(R.id.competitionsRv);

        competitions = new ArrayList<>();

        viewModel = ViewModelProviders.of(this).get(CompetitionListViewModel.class);

        loadCompetitions();

    }

    private void loadCompetitions() {
        viewModel.getCompetitionResource().observe(this, competitionResource -> {
            competitions.clear();
            competitions = competitionResource.getCompetitions();
            System.out.println(competitions);
            populateRecyclerView();
        });
        viewModel.getCompetitions();
    }


    public void populateRecyclerView() {
        competitionAdapter = new CompetitionAdapter(filterList());
        competitionsRv.setAdapter(competitionAdapter);
        competitionsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        competitionAdapter.setOnItemClickListener(new CompetitionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Competition competition) {
                Intent intent = new Intent(getActivity(), TeamListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", competition.getId());
                bundle.putString("name", competition.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public List<Competition> filterList() {
        List<Competition> filteredList = new ArrayList<>();
        for (int i = 0; i < competitions.size(); i++) {
            if (competitions.get(i).getCompCheck() != 1) {
                filteredList.add(competitions.get(i));
            }
        }
        return filteredList;
    }
}
