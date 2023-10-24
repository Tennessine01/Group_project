package com.ptit.englishapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.Dic109K;

import java.util.List;

public class Dic109KAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dic109K> mList;

    private IDic109KListener listener;

    private IDicShowInfoListener infoListener;

    private static final int TYPE_ITEM = 1;

    private static final int TYPE_LOADING = 2;

    private boolean isLoadingAdd;

    @Override
    public int getItemViewType(int position) {
        if (mList != null && position == mList.size() - 1 && isLoadingAdd) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    public void setData(List<Dic109K> mList, IDic109KListener listener) {
        this.listener = listener;
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addMList(List<Dic109K> mList) {
        int startPosition = this.mList.size();
        this.mList.addAll(mList);
        notifyItemRangeInserted(startPosition, mList.size());
    }

    public void setData(IDicShowInfoListener listener) {
        this.infoListener = listener;
    }

    public void setData(List<Dic109K> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_109k, parent, false);
            return new Dic109KViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_109k_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            Dic109K dic109K = mList.get(position);
            if (dic109K == null) {
                return;
            }
            Dic109KViewHolder dic109KViewHolder = (Dic109KViewHolder) holder;
            dic109KViewHolder.word.setText(dic109K.getWord());
            dic109KViewHolder.phonetic.setText(dic109K.getPhonetic());
            dic109KViewHolder.mean.setText(dic109K.getMean());
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class Dic109KViewHolder extends RecyclerView.ViewHolder {

        private TextView word;
        private TextView phonetic;
        private TextView mean;
        private ImageButton imageButton;

        public Dic109KViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.word109K);
            phonetic = itemView.findViewById(R.id.phonetic109K);
            mean = itemView.findViewById(R.id.mean109K);
            imageButton = itemView.findViewById(R.id.read109K);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(mList.get(getAdapterPosition()));
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    infoListener.onClick(mList.get(getAdapterPosition()));
                }
            });
        }
    }

    public class LoadingViewHolder extends  RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar_109k);
        }
    }

    public void addFooterLoading() {
        isLoadingAdd = true;
        mList.add(new Dic109K());
    }

    public void removeFooterLoading() {
        isLoadingAdd = false;
        int position = mList.size() - 1;
        Dic109K dic109K = mList.get(position);
        if (dic109K != null) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface IDic109KListener {
        void onClick(Dic109K dic109K);
    }

    public interface IDicShowInfoListener {
        void onClick(Dic109K dic109K);
    }
}
