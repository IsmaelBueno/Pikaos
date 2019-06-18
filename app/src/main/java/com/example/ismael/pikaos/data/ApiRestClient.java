package com.example.ismael.pikaos.data;

import com.example.ismael.pikaos.UI.PikaosApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRestClient {
    private static ApiService API_SERVICE;

    //http://157.230.114.223:8090/
    public static final String BASE_URL = PikaosApplication.URL;

    public static synchronized ApiService getInstance() {

        if (API_SERVICE == null) {

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS);

            Gson gson = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpBuilder.build())
                    .build();

            API_SERVICE = retrofit.create(ApiService.class);
        }
        return API_SERVICE;
    }
}

