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

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private List<Team> mTeams;
    private Context context;
    private OnItemClickListener listener;


    public TeamAdapter(List<Team> mTeams) {
        this.mTeams = mTeams;
    }

    @NonNull
    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.team_row, null);
        TeamAdapter.ViewHolder viewHolder = new TeamAdapter.ViewHolder(view);
        return viewHolder;
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


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamTextView;
        ImageView teamImageView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamTextView = itemView.findViewById(R.id.teamTextView);
            teamImageView = itemView.findViewById(R.id.teamImageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mTeams.get(position), mTeams.get(position).getTeamName(), teamImageView);
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
