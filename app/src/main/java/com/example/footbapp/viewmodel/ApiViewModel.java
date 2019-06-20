package com.example.footbapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.footbapp.repository.TheSportsDbRepository;
import com.example.footbapp.model.CompetitionResource;
import com.example.footbapp.model.EventResource;
import com.example.footbapp.model.TeamResource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel class for The Sports Db Api
 *
 */
public class ApiViewModel extends AndroidViewModel {
    private final TheSportsDbRepository theSportsDbRepository = new TheSportsDbRepository();

    private final MutableLiveData<CompetitionResource> competitionResource = new MutableLiveData<>();
    private final MutableLiveData<TeamResource> teamResource = new MutableLiveData<>();
    private final MutableLiveData<EventResource> eventResource = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loaded = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

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
                    public void onResponse(@NonNull Call<CompetitionResource> call, @NonNull Response<CompetitionResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            competitionResource.setValue(response.body());
                            dataRetrieved();
                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CompetitionResource> call, @NonNull Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }

    public void getCompetitionById(String competitionId) {
        theSportsDbRepository
                .getCompetitionById(competitionId)
                .enqueue(new Callback<TeamResource>() {
                    @Override
                    public void onResponse(@NonNull Call<TeamResource> call, @NonNull Response<TeamResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            teamResource.setValue(response.body());
                            dataRetrieved();

                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TeamResource> call, @NonNull Throwable t) {
                        error.setValue("API error: " + t.getMessage());

                    }
                });
    }

    public void getEventsById(String teamId) {
        theSportsDbRepository
                .getEventsById(teamId)
                .enqueue(new Callback<EventResource>() {
                    @Override
                    public void onResponse(@NonNull Call<EventResource> call, @NonNull Response<EventResource> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            eventResource.setValue(response.body());
                            dataRetrieved();

                        } else {
                            error.setValue("API error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EventResource> call, @NonNull Throwable t) {
                        error.setValue("API error: " + t.getMessage());
                    }
                });
    }
}
