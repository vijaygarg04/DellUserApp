package com.example.shivam.delluserapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//This is my splash screem
    Handler handler;
    ProgressBar progressBar;
    int progressBarTimer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar)findViewById(R.id.progress);
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
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

               if (progressBarTimer==100 ){

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }

        }).start();



    }
}