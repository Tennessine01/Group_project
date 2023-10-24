package com.ptit.englishapp.app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.englishapp.MainActivity;
import com.ptit.englishapp.R;
import com.ptit.englishapp.service.TestData;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class PronounceActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 102;

    private EditText sysPronounce, userPronounce;
    private ImageView micro;

    private ImageButton speak;
    private Button nextWord, checkWord;
    private TextView rate;

    private TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounce);

        initView();

        nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sysPronounce.setText(TestData.getRandomSentence());
            }
        });

        checkWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PronounceActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak convert into text");
                try {
                    startActivityForResult(i, REQUEST_PERMISSION_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PronounceActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(sysPronounce.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                userPronounce.setText(result.get(0));
                double accuracy = TestData.compareSentencesPronunciation(sysPronounce.getText().toString(),  userPronounce.getText().toString());
                String evaluate = TestData.evaluatePronunciationAccuracy(accuracy);
                rate.setText(String.format("Độ chính xác: %.0f%%, %s", accuracy, evaluate));
                rate.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        sysPronounce = findViewById(R.id.systemPronounce);
        userPronounce = findViewById(R.id.userPronounce);
        micro = findViewById(R.id.btnMicPronouce);
        nextWord = findViewById(R.id.btNextWord);
        checkWord = findViewById(R.id.btnTextPronounce);
        rate = findViewById(R.id.rate);
        speak = findViewById(R.id.speakPronounce);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void playSound(String word) {
        String utteranceId = UUID.randomUUID().toString();
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
}