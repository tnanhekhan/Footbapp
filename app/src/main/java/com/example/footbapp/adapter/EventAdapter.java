package com.example.footbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.footbapp.R;
import com.example.footbapp.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<Event> events;
    private List<Event> subscribedEvents = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;


    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_row, null);
        EventAdapter.ViewHolder viewHolder = new EventAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.eventLeagueTextView.setText(events.get(i).getStrLeague());
        viewHolder.eventTimeTextView.setText(events.get(i).getStrTime());
        viewHolder.eventDateTextView.setText(events.get(i).getDateEvent());
        viewHolder.eventHomeTeamTextView.setText(events.get(i).getStrHomeTeam());
        viewHolder.eventAwayTeamTextView.setText(events.get(i).getStrAwayTeam());
        if (events.get(i).getDateEvent() == null) {
            viewHolder.notificationIconImageView.setVisibility(View.INVISIBLE);
        }

        for (int j = 0; j < subscribedEvents.size(); j++) {
            if (subscribedEvents.get(j).getIdEvent() == events.get(i).getIdEvent()) {
                viewHolder.notificationIconImageView.setImageResource(R.drawable.ic_notifications_black_24dp);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventLeagueTextView;
        TextView eventTimeTextView;
        TextView eventDateTextView;
        TextView eventHomeTeamTextView;
        TextView eventAwayTeamTextView;
        ImageView notificationIconImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventLeagueTextView = itemView.findViewById(R.id.eventLeagueTextView);
            eventTimeTextView = itemView.findViewById(R.id.eventTimeTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
            eventHomeTeamTextView = itemView.findViewById(R.id.eventHomeTeamTextView);
            eventAwayTeamTextView = itemView.findViewById(R.id.eventAwayTeamTextView);
            notificationIconImageView = itemView.findViewById(R.id.notificationIconImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(events.get(position), notificationIconImageView);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Event event, ImageView notificationIcon);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
