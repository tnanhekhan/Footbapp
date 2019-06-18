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

public class FavoriteTeamAdapter extends RecyclerView.Adapter<FavoriteTeamAdapter.ViewHolder> {
    private List<Team> favoriteTeams;
    private OnItemClickListener listener;
    private Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorite_team_row, viewGroup, false);
        return new ViewHolder(itemView);
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


    public void setFavoriteTeams(List<Team> favoriteTeams) {
        this.favoriteTeams = favoriteTeams;
        notifyDataSetChanged();
    }

    public Team getTeamAt(int position) {
        return favoriteTeams.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView favoriteTeamNameTextView;
        ImageView favoriteTeamImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favoriteTeamNameTextView = itemView.findViewById(R.id.favoriteTeamNameTextView);
            favoriteTeamImageView = itemView.findViewById(R.id.favoriteTeamImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(favoriteTeams.get(position), favoriteTeams.get(position).getTeamName(), favoriteTeamImageView);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Team team, String teamName, ImageView imageView);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}