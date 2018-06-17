package com.example.shivam.delluserapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class MainActivity extends AppCompatActivity {
//This is my splash screem

    TinyDB tinyDB ;
    ImageView image;
    Handler handler;
    ProgressBar progressBar;
    boolean b;
    int progressBarTimer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        tinyDB = new TinyDB(MainActivity.this);
        progressBar = (ProgressBar)findViewById(R.id.progress);
         b = tinyDB.getBoolean(StaticConstants.is_config_object_created);
         handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressBarTimer<100){
                    progressBarTimer+=5;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarTimer);
                        }
                    });
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(progressBarTimer==100 && b){
                    Intent intent = new Intent(MainActivity.this,NavigatorActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (progressBarTimer==100 && !b){
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        }).start();



    }
}