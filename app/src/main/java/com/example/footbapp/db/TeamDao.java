package com.example.footbapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.footbapp.model.Team;

import java.util.List;

/**
 * Dao class for Favorite Teams
 *
 */
@Dao
public interface TeamDao {

    @Insert
    void insert(Team team);

    @Delete
    void delete(Team team);

    @Update
    void update(Team team);

    @Query("DELETE FROM favorite_team")
    void deleteAllFavoriteTeams();

    @Query("SELECT * FROM favorite_team ORDER BY teamName")
    LiveData<List<Team>> getAllFavoriteTeams();
}
