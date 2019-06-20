package com.example.footbapp.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.example.footbapp.R;
import com.example.footbapp.activities.TeamListActivity;
import com.example.footbapp.adapter.CompetitionAdapter;
import com.example.footbapp.model.Competition;
import com.example.footbapp.viewmodel.ApiViewModel;
import com.example.footbapp.viewmodel.EventViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Fragment Class for the Competition List
 *
 */
public class CompetitionListFragment extends Fragment {
    private ApiViewModel apiViewModel;
    private List<Competition> competitions;
    private RecyclerView competitionsRv;
    private CompetitionAdapter competitionAdapter;
    private ProgressBar progressBar;


    public CompetitionListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressBar = Objects.requireNonNull(getActivity()).findViewById(R.id.competitionListProgressBar);

        competitionsRv = getActivity().findViewById(R.id.competitionsRv);

        competitions = new ArrayList<>();

        apiViewModel = ViewModelProviders.of(this).get(ApiViewModel.class);
        EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);

        isLoaded();
        loadCompetitions();

    }

    private void isLoaded() {
        apiViewModel.IsLoaded().observe(this, isLoaded -> {
            if (isLoaded != null) {
                if (isLoaded) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void loadCompetitions() {
        apiViewModel.getCompetitionResource().observe(this, competitionResource -> {
            competitions.clear();
            competitions = Objects.requireNonNull(competitionResource).getCompetitions();
            populateRecyclerView();
        });
        apiViewModel.getCompetitions();
    }


    private void populateRecyclerView() {
        competitionAdapter = new CompetitionAdapter(filterList());
        competitionsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        competitionsRv.setAdapter(competitionAdapter);

        competitionAdapter.setOnItemClickListener(competition -> {
            Intent intent = new Intent(getActivity(), TeamListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", competition.getId());
            bundle.putString("name", competition.getName());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    private List<Competition> filterList() {
        List<Competition> filteredList = new ArrayList<>();
        for (int i = 0; i < competitions.size(); i++) {
            final String FILTERED_COMP = "Colombia Categoría Primera A";
            if (competitions.get(i).getCompCheck() != 1 && !competitions.get(i).getName().equals(FILTERED_COMP)) {
                filteredList.add(competitions.get(i));
            }
        }
        return filteredList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                competitionAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

}
