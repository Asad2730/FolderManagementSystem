package com.example.biit.Upload;
import com.example.biit.Db.Db;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit= null;
    public  static Retrofit getClient(){
        HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client= new OkHttpClient.Builder().
                readTimeout(60, TimeUnit.SECONDS).
                connectTimeout(60,TimeUnit.SECONDS).
                writeTimeout(10,TimeUnit.MINUTES).
                addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://"+Db.ip+"/FypApi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}