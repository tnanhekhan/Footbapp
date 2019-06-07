package com.example.footbapp.model;

import java.util.List;

public class TeamResource {
    private List<Team> teams;

    public TeamResource(int count, List teams) {
        this.teams = teams;
    }

    public List getTeams() {
        return teams;
    }

    public void setTeams(List teams) {
        this.teams = teams;
    }
}
