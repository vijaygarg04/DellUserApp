package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class MainActivity extends AppCompatActivity {
//This is my splash screem
    TinyDB tinyDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tinyDB = new TinyDB(MainActivity.this);
        boolean b = tinyDB.getBoolean(StaticConstants.is_config_object_created);
        if (b){
            Intent intent = new Intent(MainActivity.this,NavigatorActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}