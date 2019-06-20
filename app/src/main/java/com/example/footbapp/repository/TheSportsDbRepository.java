package com.example.footbapp.repository;

import com.example.footbapp.api.TheSportsDbApi;
import com.example.footbapp.api.TheSportsDbService;
import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.EventResource;
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

    public Call<EventResource> getEventsById(String teamId) {
        return theSportsDbService.getEventsById(teamId);
    }
}
