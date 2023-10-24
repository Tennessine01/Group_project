package com.ptit.englishapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptit.englishapp.R;
import com.ptit.englishapp.adapter.WordAdapter;
import com.ptit.englishapp.model.DatabaseAccess;
import com.ptit.englishapp.model.Word;

import java.util.List;

public class ListWordActivity extends AppCompatActivity {
    ListView lavWord;
    WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_word);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String topic = bundle.getString("id", "");
            LoadData(topic);
        }
    }

    private void LoadData(String topic) {
        lavWord = findViewById(R.id.listWord);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<Word> list = databaseAccess.GetTopic(topic);
        adapter = new WordAdapter(ListWordActivity.this, R.layout.word_item, list);
        lavWord.setAdapter(adapter);
        // databaseAccess.close();
    }
}
