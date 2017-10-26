package com.jesus.test.catalog.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jesus.test.catalog.MyApplication;
import com.jesus.test.catalog.R;
import com.jesus.test.catalog.models.App;
import com.jesus.test.catalog.MyApplication;

import java.text.DecimalFormat;

public class AppDetailActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    public App myApp;
    boolean fav = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Obteniendo la data del app
        Gson gson = new Gson();
        String appString = getIntent().getStringExtra("appString");
        myApp =  gson.fromJson(appString, App.class);
        /****/


        setTitle(myApp.getTitle().getLabel());
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabBottonAction();
        fillDetail();

    }

    private void fillDetail() {
        ImageView imageView =  (ImageView) findViewById(R.id.appImageDetail);
        Glide.with(getApplicationContext())
                .load(myApp.getImImage().get(2).getLabel())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        TextView appCategory = (TextView) findViewById(R.id.appCategory);
        appCategory.setText((CharSequence) myApp.getCategory().getAttributes().getLabel());

        TextView appPrice = (TextView) findViewById(R.id.appPrice);
        String price = myApp.getImPrice().getAttributes().getAmount() ;
        if(price.equals("0.00000")){
            price = "Free";
        }else{
            double aux = Double.parseDouble(price);
            price = (new DecimalFormat("##.##").format(aux)).toString() + "€";
        }
        appPrice.setText((CharSequence) price);


        TextView appRelease = (TextView) findViewById(R.id.appRelease);
        appRelease.setText(myApp.getImReleaseDate().getAttributes().getLabel());


        TextView appRights = (TextView) findViewById(R.id.appRights);
        appRights.setText(myApp.getRights().getLabel());

        TextView appSummary = (TextView) findViewById(R.id.appSummary);
        appSummary.setText(myApp.getSummary().getLabel());


        CheckBox appFavoriteCheck = (CheckBox) findViewById(R.id.check_fav);
        if(myApp.getFav() == true){
            appFavoriteCheck.setChecked(true);
        }
        appFavoriteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fav = true;
                    changeState();
                }else{
                    fav = false;
                    changeState();
                }

            }
        });




    }

    private void fabBottonAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Loading", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myApp.getLink().getAttributes().getHref()));
                startActivity(browserIntent);
            }
        });
    }

    private void changeState(){
        if(fav){
            ((MyApplication) this.getApplication()).actFeed(myApp, true);
        }else{
            ((MyApplication) this.getApplication()).actFeed(myApp, false);
        }
    }

}
