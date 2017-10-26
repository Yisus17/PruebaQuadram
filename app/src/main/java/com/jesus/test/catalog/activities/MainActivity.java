package com.jesus.test.catalog.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.jesus.test.catalog.MyApplication;
import com.jesus.test.catalog.R;
import com.jesus.test.catalog.api.ItunesService;
import com.jesus.test.catalog.models.Feed;
import com.jesus.test.catalog.models.ItunesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences mPrefs;
    public SharedPreferences.Editor prefsEditor;
    private Retrofit retrofit;
    private final String URL = "https://itunes.apple.com/";

    Feed feed, payFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mPrefs = getPreferences(MODE_PRIVATE);
        prefsEditor = mPrefs.edit();

        getFeed();
    }

    //Instacia del Feed (Retrofit si hay conexión, en caso contrario SharedPrefferences
    private void getFeed() {
        ItunesService service = retrofit.create(ItunesService.class);
        Call<ItunesResponse> itunesResponseCall = service.getiTunesFeed();

        itunesResponseCall.enqueue(new Callback<ItunesResponse>() {
            @Override
            public void onResponse(Call<ItunesResponse> call, Response<ItunesResponse> response) {
                if (response.isSuccessful()) {
                    ItunesResponse itunesResponse = response.body();
                    feed = itunesResponse.getFeed();
                    //saveData(feed);
                    getPayFeed();
                    //sendData(feed);
                } else {
                    Log.e("APLICACION", "onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<ItunesResponse> call, Throwable t) {
                Log.e("APLICACION", "onFailure: " + t.getMessage());
                //sendData(getSavedData()); //Usar los datos previamente cargados
            }
        });

    }

    private void getPayFeed(){
        ItunesService service = retrofit.create(ItunesService.class);
        Call<ItunesResponse> itunesResponseCall = service.getiTunesFeedPay();

        itunesResponseCall.enqueue(new Callback<ItunesResponse>() {
            @Override
            public void onResponse(Call<ItunesResponse> call, Response<ItunesResponse> response) {
                if (response.isSuccessful()) {
                    ItunesResponse itunesResponse = response.body();
                    payFeed = itunesResponse.getFeed();
                    saveData();
                    sendData();
                } else {
                    Log.e("APLICACION", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ItunesResponse> call, Throwable t) {
                Log.e("APLICACION", "onFailure: " + t.getMessage());
                getSavedData();
                sendData(); //Usar los datos previamente cargados
            }
        });
    }



    private void sendData() {
        Gson gson = new Gson();

        String feedString = gson.toJson(feed);
        String payFeedString = gson.toJson(payFeed);

        Intent intent = new Intent(MainActivity.this, TabsActivity.class);
        intent.putExtra("feedString", feedString);
        intent.putExtra("payFeedString", payFeedString);

        ((MyApplication) this.getApplication()).setFeed(feed);
        ((MyApplication) this.getApplication()).setPayFeed(payFeed);


        startActivity(intent);
        finish();
    }

    private void saveData() {
        Gson gson = new Gson();
        String feedString = gson.toJson(feed);
        String payFeedString = gson.toJson(payFeed);

        prefsEditor.putString("payFeed", payFeedString);
        prefsEditor.commit();

        prefsEditor.putString("feed", feedString);
        prefsEditor.commit();

        Log.i("APLICACION", feedString);
    }

    private void getSavedData() {
        Gson gson = new Gson();
        String json = mPrefs.getString("feed", "");
        String json2 = mPrefs.getString("payFeed", "");

        feed = gson.fromJson(json, Feed.class);
        payFeed = gson.fromJson(json2, Feed.class);

    }

}
