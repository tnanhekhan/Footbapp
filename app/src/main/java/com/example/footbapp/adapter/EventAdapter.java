package com.example.footbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.footbapp.R;
import com.example.footbapp.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Recyclerview Adapter used in the Event List activity and in the Overview Fragment
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final List<Event> events;
    private List<Event> subscribedEvents = new ArrayList<>();
    private final CheckBoxClickListener listener;


    public EventAdapter(List<Event> events, CheckBoxClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.eventLeagueTextView.setText(events.get(i).getStrLeague());
        viewHolder.eventTimeTextView.setText(events.get(i).getStrTime());
        viewHolder.eventDateTextView.setText(events.get(i).getDateEvent());
        viewHolder.eventHomeTeamTextView.setText(events.get(i).getStrHomeTeam());
        viewHolder.eventAwayTeamTextView.setText(events.get(i).getStrAwayTeam());
        if (events.get(i).getDateEvent() == null) {
            viewHolder.notificationCheckBox.setVisibility(View.INVISIBLE);
        }

        for (int j = 0; j < subscribedEvents.size(); j++) {
            if (subscribedEvents.get(j).getIdEvent() == events.get(i).getIdEvent()) {
                viewHolder.notificationCheckBox.setChecked(true);
                events.get(i).setSubscribed(true);
            }
        }

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setSubscribedEvents(List<Event> events) {
        subscribedEvents = events;
    }

    public Event getEventAt(int position) {
        return events.get(position);
    }

    public interface CheckBoxClickListener {
        void onItemCheck(Event event);

        void onItemUnCheck(Event event);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView eventLeagueTextView;
        final TextView eventTimeTextView;
        final TextView eventDateTextView;
        final TextView eventHomeTeamTextView;
        final TextView eventAwayTeamTextView;
        final CheckBox notificationCheckBox;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventLeagueTextView = itemView.findViewById(R.id.eventLeagueTextView);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
            eventHomeTeamTextView = itemView.findViewById(R.id.eventHomeTeamTextView);
            eventAwayTeamTextView = itemView.findViewById(R.id.eventAwayTeamTextView);
            notificationCheckBox = itemView.findViewById(R.id.notificationCheckBox);

            notificationCheckBox.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if (notificationCheckBox.isChecked()) {
                        listener.onItemCheck(events.get(position));
                    } else {
                        listener.onItemUnCheck(events.get(position));
                    }
                }
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (notificationCheckBox.isChecked()) {
                    notificationCheckBox.setChecked(false);
                    listener.onItemUnCheck(events.get(position));
                } else {
                    notificationCheckBox.setChecked(true);
                    listener.onItemCheck(events.get(position));
                }
            });

        }
    }
}
