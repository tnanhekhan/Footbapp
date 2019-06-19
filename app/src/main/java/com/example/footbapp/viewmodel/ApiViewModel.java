package com.example.footbapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.footbapp.api.FootballRepository;
import com.example.footbapp.api.TheSportsDbRepository;
import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.EventResource;
import com.example.footbapp.model.TeamResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiViewModel extends AndroidViewModel {
    private FootballRepository footballRepository = new FootballRepository();
    private TheSportsDbRepository theSportsDbRepository = new TheSportsDbRepository();

    private MutableLiveData<CompetitionResource> competitionResource = new MutableLiveData<>();
    private MutableLiveData<TeamResource> teamResource = new MutableLiveData<>();
    private MutableLiveData<EventResource> eventResource = new MutableLiveData<>();

    private MutableLiveData<Boolean> loaded = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public ApiViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CompetitionResource> getCompetitionResource() {
        return competitionResource;
    }

    public MutableLiveData<TeamResource> getTeamResource() {
        return teamResource;
    }

    public MutableLiveData<EventResource> getEventResource() {
        return eventResource;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> IsLoaded() {
        return loaded;
    }

    private void dataRetrieved() {
        loaded.postValue(true);
    }

    public void getCompetitions() {
        theSportsDbRepository.getCompetitions()
                .enqueue(new Callback<CompetitionResource>() {
                    @Override
                    public void onResponse(Call<CompetitionResource> call, Response<CompetitionResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            competitionResource.setValue(response.body());
                            dataRetrieved();
                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CompetitionResource> call, Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }

    public void getCompetitionById(String competitionId) {
        theSportsDbRepository
                .getCompetitionById(competitionId)
                .enqueue(new Callback<TeamResource>() {
                    @Override
                    public void onResponse(Call<TeamResource> call, Response<TeamResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            teamResource.setValue(response.body());
                            dataRetrieved();

                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<TeamResource> call, Throwable t) {
                        error.setValue("API error: " + t.getMessage());

                    }
                });
    }

    public void getEventsById(String teamId) {
        theSportsDbRepository
                .getEventsById(teamId)
                .enqueue(new Callback<EventResource>() {
                    @Override
                    public void onResponse(Call<EventResource> call, Response<EventResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            eventResource.setValue(response.body());
                            dataRetrieved();

                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EventResource> call, Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }
}
