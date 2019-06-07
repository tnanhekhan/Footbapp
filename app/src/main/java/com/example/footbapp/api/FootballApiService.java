package com.example.footbapp.api;

import com.example.footbapp.model.Competition;
import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.TeamResource;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FootballApiService {

    @GET("/v2/competitions")
    Call<CompetitionResource> getCompetitions(@Header("authorization") String auth, @Query("plan") String tierPlan);

    @GET("/v2/competitions/{compId}")
    Call<Competition>getCompetitionById(@Header("authorization") String auth, @Path("compId") String competitionId);
}
