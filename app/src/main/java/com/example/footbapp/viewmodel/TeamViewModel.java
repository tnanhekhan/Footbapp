package com.example.footbapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.footbapp.model.Team;
import com.example.footbapp.repository.TeamRepository;

import java.util.List;

/**
 * ViewModel class for Favorite Teams
 *
 */
public class TeamViewModel extends AndroidViewModel {
    private final TeamRepository teamRepository;
    private final LiveData<List<Team>> allTeams;

    public TeamViewModel(@NonNull Application application) {
        super(application);
        teamRepository = new TeamRepository(application);
        allTeams = teamRepository.getAllFavoriteTeams();
    }

    public void insert(Team team) {
        teamRepository.insert(team);
    }

    public void delete(Team team) {
        teamRepository.delete(team);
    }

    public void deleteAllFavoriteTeams() {
        teamRepository.deleteAllFavoriteTeams();
    }

    public LiveData<List<Team>> getAllFavoriteTeams() {
        return allTeams;
    }
}
