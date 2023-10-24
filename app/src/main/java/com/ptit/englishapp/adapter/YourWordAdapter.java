package com.ptit.englishapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.YourWord;

import java.util.List;
import java.util.zip.Inflater;

public class YourWordAdapter extends RecyclerView.Adapter<YourWordAdapter.YourWordViewHolder> {

    private List<YourWord> mList;

    private OnClickListener mListener;

    public void setMListener(OnClickListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<YourWord> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YourWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_word, parent, false);
        return new YourWordViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull YourWordViewHolder holder, int position) {
        YourWord yourWord = mList.get(position);
        if (yourWord == null) {
            return;
        }
        holder.imageView.setImageResource(R.drawable.bg_word);
        holder.id.setText(yourWord.getId().toString());
        holder.name.setText(yourWord.getWord());
        holder.phonetic.setText(yourWord.getPhonetic());
        holder.mean.setText(yourWord.getMean());
        holder.desc.setText(yourWord.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(yourWord);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }

    public class YourWordViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView id, name, phonetic, mean, desc;

        public YourWordViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.backgroundYourWord);
            id = itemView.findViewById(R.id.idYourWord);
            name = itemView.findViewById(R.id.yourWordName);
            phonetic = itemView.findViewById(R.id.phoneticYW);
            mean = itemView.findViewById(R.id.meanYW);
            desc = itemView.findViewById(R.id.descYW);
        }
    }

    public interface OnClickListener {
        void onClick(YourWord yourWord);
    }
}
