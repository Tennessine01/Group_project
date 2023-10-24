package com.ptit.englishapp.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ptit.englishapp.R;
import com.ptit.englishapp.adapter.SearchResultAdapter;
import com.ptit.englishapp.model.DatabaseAccess;
import com.ptit.englishapp.model.Word;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;
import com.ptit.englishapp.utils.Constant;

import java.util.List;


public class SearchActivity extends AppCompatActivity {

    ImageButton btnSearch;
    private EditText edttInput;
    private ListView lstResult;
    private SearchResultAdapter adapter;

    FirebaseHelper helper = new FirebaseHelper();
    String uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_search);

        edttInput = findViewById(R.id.editTextInput);
        lstResult = findViewById(R.id.list_search_result);
        btnSearch = findViewById(R.id.imageButtonSearch);
        uuid = getUIDCurrentUser();
        final Context ct = SearchActivity.this;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(ct);
        databaseAccess.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String strSearch = extras.getString("search");
            if (strSearch != null) {
                helper.addRecentData(uuid, strSearch, Constant.TypeDictionary.DIC3K);
                edttInput.setText(strSearch);
                List<Word> list = databaseAccess.getWord(strSearch);
                adapter = new SearchResultAdapter(SearchActivity.this, R.layout.search_result_item, list);
                lstResult.setAdapter(adapter);
            }
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = edttInput.getText().toString().toLowerCase();
                helper.addRecentData(uuid, input, Constant.TypeDictionary.DIC3K);
                List<Word> list = databaseAccess.getWord(input);
                adapter = new SearchResultAdapter(SearchActivity.this, R.layout.search_result_item, list);
                lstResult.setAdapter(adapter);
            }
        });
    }

    public String getUIDCurrentUser() {
        String uid = null;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        }
        if (uid != null) return uid;

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            uid = account.getId();
        }
        return uid;
    }
}
