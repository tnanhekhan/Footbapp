package com.example.footbapp.model;

import java.util.List;

public class CompetitionResource {
    private List<Competition> countrys;

    public CompetitionResource(List countrys) {
        this.countrys = countrys;
    }

    public List getCompetitions() {
        return countrys;
    }

    public void setCompetitions(List competitions) {
        this.countrys = competitions;
    }
}
