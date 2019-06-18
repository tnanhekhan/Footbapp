package com.example.footbapp.api;

import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.EventResource;
import com.example.footbapp.model.TeamResource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheSportsDbService {

    @GET("search_all_leagues.php")
    Call<CompetitionResource> getCompetitions(@Query("s") String sport);

    @GET("lookup_all_teams.php")
    Call<TeamResource>getCompetitionById(@Query("id") String competitionId);

    @GET("eventsnext.php")
    Call<EventResource> getEventsById(@Query("id") String teamId);
}
