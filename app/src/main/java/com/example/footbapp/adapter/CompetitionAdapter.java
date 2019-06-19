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
import com.example.footbapp.model.Competition;

import java.util.ArrayList;
import java.util.List;

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.ViewHolder> implements Filterable {
    private List<Competition> mCompetitions;
    private List<Competition> competitionsListFull;
    private Context context;
    private OnItemClickListener listener;


    public CompetitionAdapter(List<Competition> mCompetitions) {
        this.mCompetitions = mCompetitions;
        competitionsListFull = new ArrayList<>(mCompetitions);
    }

    @NonNull
    @Override
    public CompetitionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.competition_row, null);
        CompetitionAdapter.ViewHolder viewHolder = new CompetitionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompetitionAdapter.ViewHolder viewHolder, int i) {
        viewHolder.competitionTextView.setText(mCompetitions.get(i).getName());
        Glide.with(context).load(mCompetitions.get(i).getEmblemUrl()).placeholder(R.drawable.ic_trophy).into(viewHolder.competitionImageView);
    }

    @Override
    public int getItemCount() {
        return mCompetitions.size();
    }

    @Override
    public Filter getFilter() {
        return competitionFilter;
    }

    private Filter competitionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Competition> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(competitionsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Competition competition : competitionsListFull) {
                    if (competition.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(competition);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCompetitions.clear();
            mCompetitions.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView competitionTextView;
        ImageView competitionImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            competitionTextView = itemView.findViewById(R.id.competitionNameTextView);
            competitionImageView = itemView.findViewById(R.id.competitionImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mCompetitions.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Competition competition);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
