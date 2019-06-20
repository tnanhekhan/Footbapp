package com.example.footbapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.footbapp.model.Event;
import com.example.footbapp.repository.EventRepository;

import java.util.List;

/**
 * Viewmodel class for Subscribed Events
 *
 */
public class EventViewModel extends AndroidViewModel {
    private final EventRepository eventRepository;
    private final LiveData<List<Event>> allSubscribedEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allSubscribedEvents = eventRepository.getAllSubscribedEvents();
    }

    public void insert(Event event) {
        eventRepository.insert(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public void deletAllSubscribedEvents() {
        eventRepository.deleteAllFavoriteTeams();
    }

    public LiveData<List<Event>> getAllSubscribedEvents() {
        return allSubscribedEvents;
    }


}
