package com.ptit.englishapp.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.Article;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    private List<Article> mListArticle;

    private IListenerArticle listen;

    public void setDate(List<Article> articleList, IListenerArticle listen) {
        this.mListArticle = articleList;
        this.listen = listen;
        notifyDataSetChanged();
    }

    public void setMListArticle(List<Article> mListArticle) {
        this.mListArticle = mListArticle;
        notifyDataSetChanged();
    }

    public void setListen(IListenerArticle listen) {
        this.listen = listen;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = mListArticle.get(position);
        if (article == null) {
            return;
        }
        holder.title.setText(article.getTitle());
        Picasso.get().load(article.getUrl()).into(holder.imgArticle);
        holder.type.setText(article.getType() == 1 ? "Tin tức" : "Video");
        holder.time.setText(formatTime(article.getTime()));
        holder.luotXem.setText(article.getCare() + ((article.getCare() <= 1) ? " view" : " views"));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String formatTime(long time) {
        LocalDate today = LocalDate.now();

        Instant instant = Instant.ofEpochMilli(time);
        LocalDate targetDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(today, targetDate);

        if (daysBetween == 0) {
            return "Gần đây";
        } else if (daysBetween == 1) {
            return "Hôm qua";
        } else {
            return daysBetween + " ngày trước";
        }
    }

    @Override
    public int getItemCount() {
        if (mListArticle != null) {
            return mListArticle.size();
        }
        return 0;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private ImageView imgArticle;
        private TextView type;
        private TextView time;
        private TextView luotXem;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            imgArticle = itemView.findViewById(R.id.img_article);
            type = itemView.findViewById(R.id.tvType);
            time = itemView.findViewById(R.id.tvTime);
            luotXem = itemView.findViewById(R.id.tvLuotXem);
            // todo update time;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listen != null) {
                listen.onClick(mListArticle.get(getAdapterPosition()));
            }
        }
    }

    public interface IListenerArticle {
        void onClick(Article article);
    }
}
