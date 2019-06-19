package com.example.footbapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@Entity(tableName = "subscribed_event", foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "idTeam",
        childColumns = "idTeam",
        onDelete = ForeignKey.CASCADE), indices = {@Index("idTeam")})
public class Event implements Serializable {

    @PrimaryKey
    @Expose
    private int idEvent;
    @Expose
    private int intRound;
    @Expose
    private String strEvent;
    @Expose
    private String strHomeTeam;
    @Expose
    private String strAwayTeam;
    @Expose
    private String dateEvent;
    @Expose
    private String strTime;
    @Expose
    private String idHomeTeam;
    @Expose
    private String idAwayTeam;
    @Expose
    private String strLeague;

    private boolean subscribed;

    private String idTeam;

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIntRound() {
        return intRound;
    }

    public void setIntRound(int intRound) {
        this.intRound = intRound;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public void setStrEvent(String strEvent) {
        this.strEvent = strEvent;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public void setStrHomeTeam(String strHomeTeam) {
        this.strHomeTeam = strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public void setStrAwayTeam(String strAwayTeam) {
        this.strAwayTeam = strAwayTeam;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(String idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public String getIdAwayTeam() {
        return idAwayTeam;
    }

    public void setIdAwayTeam(String idAwayTeam) {
        this.idAwayTeam = idAwayTeam;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(String idTeam) {
        this.idTeam = idTeam;
    }
}
