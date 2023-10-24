package com.ptit.englishapp.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ptit.englishapp.R;
import com.ptit.englishapp.oxford.APIResponse;
import com.ptit.englishapp.oxford.MeaningAdapter;
import com.ptit.englishapp.oxford.PhoneticAdapter;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;
import com.ptit.englishapp.service.OnFetchDataListener;
import com.ptit.englishapp.service.RequestManager;
import com.ptit.englishapp.utils.Constant;

public class OxfordActivity extends AppCompatActivity {
    SearchView search_view;
    TextView textView_word;
    RecyclerView recycler_phonetics, recycler_meanings;
    ProgressDialog dialog;
    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    Toolbar toolbar;
    private PackageInfo mPackageInfo;

    FirebaseHelper helper = new FirebaseHelper();

    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxford);

        uuid = getUIDCurrentUser();
        search_view = findViewById(R.id.search_view);
        textView_word = findViewById(R.id.textView_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        toolbar = findViewById(R.id.toolbar);
        dialog = new ProgressDialog(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String strSearch = extras.getString("search");
            dialog.setTitle("Loading...");
            dialog.show();
            if (strSearch != null) {
                search_view.setQuery(strSearch, false);
                RequestManager manager = new RequestManager(OxfordActivity.this);
                manager.getWordMeaning(listener, strSearch);
            } else {
                RequestManager manager = new RequestManager(OxfordActivity.this);
                manager.getWordMeaning(listener, "Hello");
            }
        }


        try {
            mPackageInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            setupVersionInfo();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


//        RequestManager manager = new RequestManager(OxfordActivity.this);
//        manager.getWordMeaning(listener, "hello");

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching response for " + query);
                dialog.show();
                helper.addRecentData(uuid, query, Constant.TypeDictionary.OXFORD);
                RequestManager manager = new RequestManager(OxfordActivity.this);
                manager.getWordMeaning(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            dialog.dismiss();
            if (apiResponse==null){
                Toast.makeText(OxfordActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(apiResponse);

        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(OxfordActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(APIResponse response){
        textView_word.setText("Word: " + response.getWord());

        recycler_phonetics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        phoneticAdapter = new PhoneticAdapter(this, response.getPhonetics());
        recycler_phonetics.setAdapter(phoneticAdapter);

        recycler_meanings.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        meaningAdapter = new MeaningAdapter(this, response.getMeanings());
        recycler_meanings.setAdapter(meaningAdapter);
    }

    private void setupVersionInfo() {

        if (mPackageInfo != null) {
            String vinfo = String.format("V: %s", mPackageInfo.versionName);
            toolbar.setSubtitle(vinfo);
        }

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