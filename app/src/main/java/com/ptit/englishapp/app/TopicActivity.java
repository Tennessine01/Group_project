package com.ptit.englishapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptit.englishapp.R;
import com.ptit.englishapp.adapter.TopicAdapter;
import com.ptit.englishapp.model.DatabaseAccess;
import com.ptit.englishapp.model.Topic;

import java.util.List;

public class TopicActivity extends AppCompatActivity {

    ListView lsvTopics;
    TopicAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_activity_main);
        LoadData();

        lsvTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicActivity.this, LessonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(position + 1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void LoadData() {
        lsvTopics = findViewById(R.id.ltvTopics);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Topic> list = databaseAccess.GetAllTopics();
        adapter = new TopicAdapter(this, R.layout.topiclaout, list);
        lsvTopics.setAdapter(adapter);
    }
}
