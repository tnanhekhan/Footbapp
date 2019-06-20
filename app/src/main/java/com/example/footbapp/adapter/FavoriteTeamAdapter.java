package com.example.footbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.footbapp.R;
import com.example.footbapp.model.Team;

import java.util.List;

/**
 * Recyclerview Adapter used in the Overview fragment
 *
 */
public class FavoriteTeamAdapter extends RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder> {
    private final List<Team> favoriteTeams;
    private OnItemClickListener listener;
    private Context context;

    public FavoriteTeamAdapter(List<Team> favoriteTeams) {
        this.favoriteTeams = favoriteTeams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.favorite_team_row, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.favoriteTeamNameTextView.setText(favoriteTeams.get(i).getTeamName());
        Glide.with(context).load(favoriteTeams.get(i).getBadgePath()).placeholder(R.drawable.ic_soccer_ball).into(viewHolder.favoriteTeamImageView);

    }

    @Override
    public int getItemCount() {
        return favoriteTeams.size();
    }

    public Team getTeamAt(int position) {
        return favoriteTeams.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Team team);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView favoriteTeamNameTextView;
        final ImageView favoriteTeamImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteTeamNameTextView = itemView.findViewById(R.id.favoriteTeamNameTextView);
            favoriteTeamImageView = itemView.findViewById(R.id.favoriteTeamImageView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(favoriteTeams.get(position));
                }
            });
        }
    }
}
