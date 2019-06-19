package com.example.footbapp.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.footbapp.R;
import com.example.footbapp.activities.TeamListActivity;
import com.example.footbapp.adapter.CompetitionAdapter;
import com.example.footbapp.model.Competition;
import com.example.footbapp.viewmodel.ApiViewModel;

import java.util.ArrayList;
import java.util.List;


public class CompetitionListFragment extends Fragment {
    public final String FILTERED_COMP = "Colombia Categoría Primera A";
    private ApiViewModel apiViewModel;
    private List<Competition> competitions;
    private RecyclerView competitionsRv;
    private CompetitionAdapter competitionAdapter;
    private ProgressBar progressBar;

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

        progressBar = getActivity().findViewById(R.id.competitionListProgressBar);

        competitionsRv = getActivity().findViewById(R.id.competitionsRv);

        competitions = new ArrayList<>();

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);

        isLoaded();
        loadCompetitions();

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

    private void loadCompetitions() {
        apiViewModel.getCompetitionResource().observe(this, competitionResource -> {
            competitions.clear();
            competitions = competitionResource.getCompetitions();
            System.out.println(competitions);
            populateRecyclerView();
        });
        apiViewModel.getCompetitions();
    }


    public void populateRecyclerView() {
        competitionAdapter = new CompetitionAdapter(filterList());
        competitionsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        competitionsRv.setAdapter(competitionAdapter);

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
            if (competitions.get(i).getCompCheck() != 1 && !competitions.get(i).getName().equals(FILTERED_COMP)) {
                filteredList.add(competitions.get(i));
            }
        }
        return filteredList;
    }
}
