package com.example.footbapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.footbapp.model.Event;
import com.example.footbapp.model.Team;

/**
 * Room database class for the application
 *
 */
@Database(entities = {Team.class, Event.class}, version = 1, exportSchema = false)
public abstract class TeamDatabase extends RoomDatabase {

    private static TeamDatabase instance;

    public static synchronized TeamDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TeamDatabase.class, "team_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract TeamDao teamDao();

    public abstract EventDao eventDao();
}
