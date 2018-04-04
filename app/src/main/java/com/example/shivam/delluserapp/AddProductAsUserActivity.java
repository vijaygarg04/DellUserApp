package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddProductAsUserActivity extends AppCompatActivity {
//TODO : Make a layout with UI to fetch product details as admin from the user and ask for passcode in the end.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_as_user);
       Intent intent =  getIntent();
       String service_tag = intent.getStringExtra("service_tag");
    }
}
