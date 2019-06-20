package com.example.footbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.footbapp.R;
import com.example.footbapp.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Recyclerview Adapter used in the Team List Activity
 *
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> implements Filterable {
    private final List<Team> mTeams;
    private final List<Team> teamListFull;
    private Context context;
    private OnItemClickListener listener;
    private final Filter teamFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Team> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(teamListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Team team : teamListFull) {
                    if (team.getTeamName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(team);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mTeams.clear();
            mTeams.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public TeamAdapter(List<Team> mTeams) {
        this.mTeams = mTeams;
        teamListFull = new ArrayList<>(mTeams);
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.team_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewHolder viewHolder, int i) {
        viewHolder.teamTextView.setText(mTeams.get(i).getTeamName());
        Glide.with(context).load(mTeams.get(i).getBadgePath()).placeholder(R.drawable.ic_soccer_ball).into(viewHolder.teamImageView);

        viewHolder.teamImageView.setTransitionName(mTeams.get(i).getTeamName());
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return teamFilter;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Team team, String teamName, ImageView imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView teamTextView;
        final ImageView teamImageView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamTextView = itemView.findViewById(R.id.teamTextView);
            teamImageView = itemView.findViewById(R.id.teamImageView);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(mTeams.get(position), mTeams.get(position).getTeamName(), teamImageView);
                }
            });
        }
    }
}
