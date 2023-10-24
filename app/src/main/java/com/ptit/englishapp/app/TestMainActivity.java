package com.ptit.englishapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ptit.englishapp.R;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void onTopic(View view) {
        Intent intent = new Intent(TestMainActivity.this, TopicActivity.class);
        startActivity(intent);
    }

    public void onSearch2(View view) {
        Intent intent = new Intent(TestMainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void onHomeWork(View view) {
        Intent intent = new Intent(TestMainActivity.this, HomeWorkActivity.class);
        startActivity(intent);
    }

    public void onDailyWord(View view) {
        Intent intent = new Intent(TestMainActivity.this, DailyWordActivity.class);
        startActivity(intent);
    }


}
