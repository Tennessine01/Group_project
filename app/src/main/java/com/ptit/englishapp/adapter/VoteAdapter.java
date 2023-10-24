package com.ptit.englishapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.Vote;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder>{

    private List<Vote> mListVote = new ArrayList<>();

    public void setData(List<Vote> mListVote) {
        this.mListVote = mListVote;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vote, parent, false);
        return new VoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        Vote vote = mListVote.get(position);
        if (vote == null) {
            return;
        }
        holder.author.setText(vote.getAuthor());
        holder.title.setText(vote.getTitle());
        holder.ratingBar.setRating(vote.getRate());
        holder.content.setText(vote.getContent());
    }

    @Override
    public int getItemCount() {
        if (mListVote != null)
            return mListVote.size();
        return 0;
    }

    public class VoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title, author;
        private RatingBar ratingBar;
        private TextView content;

        public VoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.voteTitle);
            author = itemView.findViewById(R.id.voteName);
            ratingBar = itemView.findViewById(R.id.voteRating);
            content = itemView.findViewById(R.id.voteContent);
        }
    }
}
