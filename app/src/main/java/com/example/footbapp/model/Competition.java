package com.example.footbapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Competition {
    @SerializedName("idLeague")
    @Expose
    private int id;
    @SerializedName("strLeague")
    @Expose
    private String name;
    @SerializedName("strBadge")
    @Expose
    private String emblemUrl;
    @SerializedName("strCountry")
    @Expose
    private String country;
    @SerializedName("idCup")
    @Expose
    private int compCheck;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmblemUrl() {
        return emblemUrl;
    }

    public void setEmblemUrl(String emblemUrl) {
        this.emblemUrl = emblemUrl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCompCheck() {
        return compCheck;
    }

    public void setCompCheck(int compCheck) {
        this.compCheck = compCheck;
    }
}
