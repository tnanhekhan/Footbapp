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

    public String getName() {
        return name;
    }

    public String getEmblemUrl() {
        return emblemUrl;
    }

    public int getCompCheck() {
        return compCheck;
    }

}
