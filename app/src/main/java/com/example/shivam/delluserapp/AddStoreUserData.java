package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class AddStoreUserData extends AppCompatActivity {
    EditText location,store_name,promoter_name,mobile_number;
    Button submit_button;
    TinyDB tinyDB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store_user_data);
        location =(EditText)findViewById(R.id.location);
        store_name=  (EditText)findViewById(R.id.store_name);
        promoter_name = (EditText)findViewById(R.id.promoter_name);
        mobile_number = (EditText)findViewById(R.id.mobile_number);
        submit_button = (Button)findViewById(R.id.submit_button);
        tinyDB = new TinyDB(AddStoreUserData.this);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location.getText().toString().trim().length()>0
                        && promoter_name.getText().toString().trim().length()>0
                        && mobile_number.getText().toString().trim().length()>0
                        && store_name.getText().toString().trim().length()>0){
                    StoreConfigModel storeConfigModel = new StoreConfigModel();
                    storeConfigModel.setStoreName(store_name.getText().toString());
                    storeConfigModel.setMobile_number(mobile_number.getText().toString());
                    storeConfigModel.setLocation_of_store(location.getText().toString());
                    storeConfigModel.setPromoter_name(promoter_name.getText().toString());
                    tinyDB.putObject(StaticConstants.config_object_key,storeConfigModel);
                    tinyDB.putBoolean(StaticConstants.is_config_object_created,true);
                    Intent intent = new Intent(AddStoreUserData.this,NavigatorActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
