package com.example.footbapp.api;

import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.TeamResource;

import retrofit2.Call;

public class TheSportsDbRepository {
    private TheSportsDbService theSportsDbService = TheSportsDbApi.create();

    public Call<CompetitionResource> getCompetitions() {
        return theSportsDbService.getCompetitions("Soccer");
    }

    public Call<TeamResource> getCompetitionById(String competitionId) {
        return theSportsDbService.getCompetitionById(competitionId);
    }
}
