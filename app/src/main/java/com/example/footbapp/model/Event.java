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
    private final int idEvent;
    @Expose
    private final int intRound;
    @Expose
    private final String strEvent;
    @Expose
    private final String strHomeTeam;
    @Expose
    private final String strAwayTeam;
    @Expose
    private final String dateEvent;
    @Expose
    private final String strTime;
    @Expose
    private final String idHomeTeam;
    @Expose
    private final String idAwayTeam;
    @Expose
    private final String strLeague;

    private boolean subscribed;

    private String idTeam;

    public Event(int idEvent, int intRound, String strEvent, String strHomeTeam,
                 String strAwayTeam, String dateEvent, String strTime, String idHomeTeam,
                 String idAwayTeam, String strLeague, boolean subscribed, String idTeam) {
        this.idEvent = idEvent;
        this.intRound = intRound;
        this.strEvent = strEvent;
        this.strHomeTeam = strHomeTeam;
        this.strAwayTeam = strAwayTeam;
        this.dateEvent = dateEvent;
        this.strTime = strTime;
        this.idHomeTeam = idHomeTeam;
        this.idAwayTeam = idAwayTeam;
        this.strLeague = strLeague;
        this.subscribed = subscribed;
        this.idTeam = idTeam;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public int getIntRound() {
        return intRound;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getStrTime() {
        return strTime;
    }

    public String getIdHomeTeam() {
        return idHomeTeam;
    }

    public String getIdAwayTeam() {
        return idAwayTeam;
    }

    public String getStrLeague() {
        return strLeague;
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
