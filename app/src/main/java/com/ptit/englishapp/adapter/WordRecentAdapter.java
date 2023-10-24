package com.ptit.englishapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.WordRecent;

import java.util.List;

public class WordRecentAdapter extends RecyclerView.Adapter<WordRecentAdapter.WordRecentViewHolder> {

    private List<WordRecent> mList;

    private OnClickListener mListener;

    public void setData(List<WordRecent> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setMListener(OnClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public WordRecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_recent, parent, false);
        return new WordRecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordRecentViewHolder holder, int position) {
        WordRecent wordRecent = mList.get(position);
        if (wordRecent == null) {
            return;
        }
        holder.recent.setText(wordRecent.getRecent());
        holder.typeDictionary.setText(wordRecent.getTypeDictionary()+"");
        holder.uid.setText(wordRecent.getUid());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(wordRecent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class WordRecentViewHolder extends RecyclerView.ViewHolder {

        private TextView recent;

        private TextView typeDictionary;

        private TextView uid;

        public WordRecentViewHolder(@NonNull View itemView) {
            super(itemView);
            recent = itemView.findViewById(R.id.recentWord);
            typeDictionary = itemView.findViewById(R.id.typeDictionary);
            uid = itemView.findViewById(R.id.uidWordRecent);
        }
    }

    public interface OnClickListener {
        void onClick(WordRecent wordRecent);
    }
}
