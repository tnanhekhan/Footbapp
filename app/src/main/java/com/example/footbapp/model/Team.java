package com.example.footbapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity(tableName = "favorite_team")
public class Team implements Parcelable {
    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {

        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
    @NonNull
    @PrimaryKey
    private final String idTeam;
    @SerializedName("strTeam")
    @Expose
    private final String teamName;
    @SerializedName("strTeamBadge")
    @Expose
    private final String badgePath;
    @SerializedName("strStadiumLocation")
    @Expose
    private final String location;
    @SerializedName("strStadium")
    @Expose
    private final String stadiumName;
    @SerializedName("strStadiumThumb")
    @Expose
    private final String stadiumImage;
    @SerializedName("strDescriptionEN")
    @Expose
    private final String description;
    @SerializedName("strTeamJersey")
    private final String kitImage;

    public Team(String idTeam, String teamName, String badgePath, String location, String stadiumName, String stadiumImage, String description, String kitImage) {
        this.idTeam = idTeam;
        this.teamName = teamName;
        this.badgePath = badgePath;
        this.location = location;
        this.stadiumName = stadiumName;
        this.stadiumImage = stadiumImage;
        this.description = description;
        this.kitImage = kitImage;
    }

    public Team(Parcel in) {
        idTeam = Objects.requireNonNull(in.readString());
        teamName = in.readString();
        badgePath = in.readString();
        location = in.readString();
        stadiumName = in.readString();
        stadiumImage = in.readString();
        description = in.readString();
        kitImage = in.readString();
    }

    public String getIdTeam() {
        return idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getBadgePath() {
        return badgePath;
    }

    public String getLocation() {
        return location;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public String getStadiumImage() {
        return stadiumImage;
    }

    public String getDescription() {
        return description;
    }

    public String getKitImage() {
        return kitImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idTeam);
        dest.writeString(teamName);
        dest.writeString(badgePath);
        dest.writeString(location);
        dest.writeString(stadiumName);
        dest.writeString(stadiumImage);
        dest.writeString(description);
        dest.writeString(kitImage);
    }
}
