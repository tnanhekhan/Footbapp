package com.example.footbapp.api;

import com.example.footbapp.model.Competition;
import com.example.footbapp.model.CompetitionResource;

import retrofit2.Call;

public class FootballRepository {
    private FootballApiService footballApiService = FootballApi.create();

    public Call<CompetitionResource> getCompetitions() {
        //TODO: puth api key in config file
        return footballApiService.getCompetitions("8da590fa16b845e48d4909ba5b19c6bc", "TIER_ONE");
    }

    public Call<Competition> getCompetitionById(String competitionId){
        return footballApiService.getCompetitionById("8da590fa16b845e48d4909ba5b19c6bc", competitionId);
    }
}
