package com.example.footbapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.footbapp.model.Team;
import com.example.footbapp.repository.TeamRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private TeamRepository teamRepository;
    private LiveData<List<Team>> allTeams;

    public RoomViewModel(@NonNull Application application) {
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
