package com.example.footbapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.footbapp.db.TeamDao;
import com.example.footbapp.db.TeamDatabase;
import com.example.footbapp.model.Team;

import java.util.List;

public class TeamRepository {
    private TeamDao teamDao;
    private LiveData<List<Team>> allFavoriteTeams;

    public TeamRepository(Application application) {
        TeamDatabase database = TeamDatabase.getInstance(application);
        teamDao = database.teamDao();
        allFavoriteTeams = teamDao.getAllFavoriteTeams();
    }

    public void insert(Team team) {
        new InsertTeamAsyncTask(teamDao).execute(team);

    }

    public void delete(Team team) {
        new DeleteTeamAsyncTask(teamDao).execute(team);
    }

    public void deleteAllFavoriteTeams() {
        new DeleteAllTeamsAsyncTask(teamDao).execute();

    }

    public LiveData<List<Team>> getAllFavoriteTeams() {
        return allFavoriteTeams;
    }

    private static class InsertTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private InsertTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.insert(teams[0]);
            return null;
        }
    }

    private static class DeleteTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private DeleteTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.delete(teams[0]);
            return null;
        }
    }

    private static class DeleteAllTeamsAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private DeleteAllTeamsAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.deleteAllFavoriteTeams();
            return null;
        }
    }


}
