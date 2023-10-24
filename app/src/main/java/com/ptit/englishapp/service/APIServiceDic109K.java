package com.ptit.englishapp.service;


import com.ptit.englishapp.model.CustomPageRequest;
import com.ptit.englishapp.model.Dic109K;
import com.ptit.englishapp.model.EnglishResponse;
import com.ptit.englishapp.model.PagingModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIServiceDic109K {
    // teample
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor);

    // localhost:8088/englishvietnamese
    APIServiceDic109K apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8088/englishvietnamese/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okBuilder.build())
            .build()
            .create(APIServiceDic109K.class);

    @POST("list")
    Observable<EnglishResponse<PagingModel<List<Dic109K>>>> getListWord(@Body CustomPageRequest customPageRequest);

    @GET("byId/20")
    Call<EnglishResponse<Dic109K>> getWordById();
}
