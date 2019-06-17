package com.example.footbapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite_team")
public class Team implements Parcelable {
    @PrimaryKey
    private int idTeam;
    @SerializedName("strTeam")
    @Expose
    private String teamName;

    @SerializedName("strTeamBadge")
    @Expose
    private String badgePath;

    @SerializedName("strStadiumLocation")
    @Expose
    private String location;

    @SerializedName("strStadium")
    @Expose
    private String stadiumName;

    @SerializedName("strStadiumThumb")
    @Expose
    private String stadiumImage;

    @SerializedName("strDescriptionEN")
    @Expose
    private String description;

    @SerializedName("strTeamJersey")
    private String kitImage;

    public Team(int idTeam, String teamName, String badgePath, String location, String stadiumName, String stadiumImage, String description, String kitImage) {
        this.idTeam = idTeam;
        this.teamName = teamName;
        this.badgePath = badgePath;
        this.location = location;
        this.stadiumName = stadiumName;
        this.stadiumImage = stadiumImage;
        this.description = description;
        this.kitImage = kitImage;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBadgePath() {
        return badgePath;
    }

    public void setBadgePath(String badgePath) {
        this.badgePath = badgePath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getStadiumImage() {
        return stadiumImage;
    }

    public void setStadiumImage(String stadiumImage) {
        this.stadiumImage = stadiumImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKitImage() {
        return kitImage;
    }

    public void setKitImage(String kitImage) {
        this.kitImage = kitImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idTeam);
        dest.writeString(teamName);
        dest.writeString(badgePath);
        dest.writeString(location);
        dest.writeString(stadiumName);
        dest.writeString(stadiumImage);
        dest.writeString(description);
        dest.writeString(kitImage);
    }

    public Team(Parcel in) {
        idTeam = in.readInt();
        teamName = in.readString();
        badgePath = in.readString();
        location = in.readString();
        stadiumName = in.readString();
        stadiumImage = in.readString();
        description = in.readString();
        kitImage = in.readString();
    }

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
}
