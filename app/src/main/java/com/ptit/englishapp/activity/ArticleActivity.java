package com.ptit.englishapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.englishapp.R;
import com.ptit.englishapp.model.Article;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.UUID;

public class ArticleActivity extends AppCompatActivity {

    TextView title, content;
    ImageButton rTitle, rContent;
    ImageView img;

    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Log.d("OnInitListener", "Text to speech engine started successfully.");
                    textToSpeech.setLanguage(Locale.US);
                } else {
                    Log.d("OnInitListener", "Error starting the text to speech engine.");
                }
            }
        });
        Article article = (Article) getIntent().getSerializableExtra("article");
        title.setText(article.getTitle());
        content.setText(article.getContent());
        Picasso.get().load(article.getUrl()).into(img);
        rTitle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                playSound(article.getTitle());
            }
        });
        rContent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                playSound(article.getContent());
            }
        });
    }


    private void initView() {
        title = findViewById(R.id.titleArticle);
        content = findViewById(R.id.contentArticle);
        img = findViewById(R.id.imgArticle);
        rTitle = findViewById(R.id.btReadTitle);
        rContent = findViewById(R.id.btReadContent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void playSound(String word) {
        String utteranceId = UUID.randomUUID().toString();
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
}