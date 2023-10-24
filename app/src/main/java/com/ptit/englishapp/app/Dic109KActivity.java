package com.ptit.englishapp.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ptit.englishapp.R;
import com.ptit.englishapp.adapter.Dic109KAdapter;
import com.ptit.englishapp.model.CustomPageRequest;
import com.ptit.englishapp.model.Dic109K;
import com.ptit.englishapp.model.EnglishResponse;
import com.ptit.englishapp.model.PagingModel;
import com.ptit.englishapp.paging.PaginationScrollListener;
import com.ptit.englishapp.realtimedatabase.FirebaseHelper;
import com.ptit.englishapp.service.APIServiceDic109K;
import com.ptit.englishapp.service.TestData;
import com.ptit.englishapp.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dic109KActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;

    TextToSpeech textToSpeech;

    List<Dic109K> mList = new ArrayList<>();

    Dic109KAdapter adapter;

    Disposable mDisposable;


    private boolean isLoading = false;
    private boolean isLastPage = false;

    private static final int SIZE_OF_PAGE = 10;

    private int totalPage;
    private int currentPage = 0;

    String keySearch = "";

    String uuid;

    FirebaseHelper helper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic109_kactivity);

        searchView = findViewById(R.id.search109K);
        recyclerView = findViewById(R.id.rv109K);
        uuid = getUIDCurrentUser();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String strSearch = extras.getString("search");
            if (strSearch != null) {
                helper.addRecentData(uuid, strSearch, Constant.TypeDictionary.DIC109K);
                searchView.setQuery(strSearch, false);
                mList = null;
                currentPage = 0;
                keySearch = strSearch;
                callAPI();
            }
        }

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        adapter = new Dic109KAdapter();
//        setFirstData();
        adapter.setData(mList, new Dic109KAdapter.IDic109KListener() {
            @Override
            public void onClick(Dic109K dic109K) {
                handleSpeech(dic109K);
            }
        });
        adapter.setData(new Dic109KAdapter.IDicShowInfoListener() {
            @Override
            public void onClick(Dic109K dic109K) {
                handleShowInfoWord(dic109K);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        callAPI();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@androidx.annotation.NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading && !isLastPage && mList != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mList.size() - 1) {
                    callAPI();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                helper.addRecentData(uuid, s, Constant.TypeDictionary.DIC109K);
                mList = null;
                currentPage = 0;
                keySearch = s;
                callAPI();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void handleShowInfoWord(Dic109K dic109K) {
        final Dialog digDialog = new Dialog(this);
        digDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        digDialog.setContentView(R.layout.diglog_show_info_word109k);

        Window window = digDialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;

        window.setAttributes(windowAttribute);

        digDialog.setCancelable(true); // click ra ngoai co the tat diglog;

        TextView word, phonetic, mean;
        ImageButton read;
        word = digDialog.findViewById(R.id.word109KInfo);
        phonetic = digDialog.findViewById(R.id.phonetic109KInfo);
        mean = digDialog.findViewById(R.id.mean109KInfo);
        read = digDialog.findViewById(R.id.read109KInfo);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(dic109K.getWord());
            }
        });

        word.setText(dic109K.getWord());
        phonetic.setText(dic109K.getPhonetic());
        mean.setText(dic109K.getMean());

        digDialog.show();
    }

    private void testAPI() {
        APIServiceDic109K.apiService.getWordById().enqueue(new Callback<EnglishResponse<Dic109K>>() {
            @Override
            public void onResponse(Call<EnglishResponse<Dic109K>> call, Response<EnglishResponse<Dic109K>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Dic109KActivity.this, "" + response.body().getData().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Dic109KActivity.this, "Response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EnglishResponse<Dic109K>> call, Throwable t) {
                Toast.makeText(Dic109KActivity.this, "Error API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAPI() {
        isLoading = true;
        currentPage += 1;

        CustomPageRequest customPageRequest = new CustomPageRequest();
        customPageRequest.setKeySearch(keySearch);
        customPageRequest.setPageNumber(currentPage);
        customPageRequest.setPageSize(SIZE_OF_PAGE);

        APIServiceDic109K.apiService.getListWord(customPageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnglishResponse<PagingModel<List<Dic109K>>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull EnglishResponse<PagingModel<List<Dic109K>>> pagingModelEnglishResponse) {
                        EnglishResponse<PagingModel<List<Dic109K>>> data = pagingModelEnglishResponse;
                        List<Dic109K> list = data.getData().getContent();
                        totalPage = data.getData().getTotalPages();
                        if ((mList == null || mList.isEmpty()) && list != null && !list.isEmpty()) {
                            mList = list;
                            adapter.setData(mList);
                            if (currentPage < totalPage) {
                                adapter.addFooterLoading();
                            } else {
                                isLastPage = true;
                                mList.remove(mList.size() - 1);
                            }
                        }
                        else if (list != null && !list.isEmpty()) {
                            mList.remove(mList.size() - 1);
                            adapter.addMList(list);

                            if (currentPage < totalPage) {
                                adapter.addFooterLoading();
                            } else {
                                isLastPage = true;
                                mList.remove(mList.size() - 1);
                            }
                        }
                        isLoading = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(Dic109KActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        adapter.setData(mList);
                    }
                });
    }

    private void handleSpeech(Dic109K dic109K) {
       playSound(dic109K.getWord());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void playSound(String word) {
        String utteranceId = UUID.randomUUID().toString();
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
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