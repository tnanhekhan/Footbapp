package com.example.footbapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.footbapp.db.EventDao;
import com.example.footbapp.db.TeamDatabase;
import com.example.footbapp.model.Event;

import java.util.List;

/**
 * Repository class for Subscribed Events
 *
 */
public class EventRepository {
    private final EventDao eventDao;
    private final LiveData<List<Event>> allSubscribedEvents;

    public EventRepository(Application application) {
        TeamDatabase database = TeamDatabase.getInstance(application);
        eventDao = database.eventDao();
        allSubscribedEvents = eventDao.getAllSubscribedEvents();
    }

    public void insert(Event event) {
        new InsertEventAsyncTask(eventDao).execute(event);

    }

    public void delete(Event event) {
        new DeleteEventAsyncTask(eventDao).execute(event);
    }

    public void update(Event event) {
        new UpdateEventAsyncTask(eventDao).execute(event);
    }

    public void deleteAllFavoriteTeams() {
        new DeleteAllEventsAsyncTask(eventDao).execute();

    }

    public LiveData<List<Event>> getAllSubscribedEvents() {
        return allSubscribedEvents;
    }

    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao eventDao;

        private InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.insert(events[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao eventDao;

        private DeleteEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.delete(events[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao eventDao;

        private UpdateEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.update(events[0]);
            return null;
        }
    }

    private static class DeleteAllEventsAsyncTask extends AsyncTask<Event, Void, Void> {
        private final EventDao eventDao;

        private DeleteAllEventsAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDao.deleteAllSubscribedEvents();
            return null;
        }
    }

}
