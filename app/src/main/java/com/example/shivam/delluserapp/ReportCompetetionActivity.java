package com.example.shivam.delluserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shivam.delluserapp.DataModels.CompModel;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportCompetetionActivity extends AppCompatActivity {
    EditText others,dell,apple,hp,lenovo, acer;
    Button sub;
    FirebaseDatabase firebaseDatabase;
    StoreConfigModel storeConfigModel;
    TinyDB tinyDB;
    SimpleDateFormat simpleDateFormat;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_competetion);
        dell = (EditText)findViewById(R.id.dell);
        others = (EditText)findViewById(R.id.others);
        hp = (EditText)findViewById(R.id.hp);
        lenovo = (EditText)findViewById(R.id.lenovo);
        tinyDB = new TinyDB(ReportCompetetionActivity.this);
        simpleDateFormat = new SimpleDateFormat("ddMMyyyy", Locale.US);
        acer = (EditText)findViewById(R.id.acer);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        sub = (Button)findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dell.getText().toString().trim().length()!=0
                        && hp.getText().toString().trim().length()!=0
                        && lenovo.getText().toString().trim().length()!=0
                        && acer.getText().toString().trim().length()!=0
                        && others.getText().toString().trim().length()!=0){
                    Calendar c = Calendar.getInstance();
                    CompModel compModel = new CompModel();
                    compModel.setAcer(acer.getText().toString().trim());
                    compModel.setLenovo(lenovo.getText().toString().trim());
                    compModel.setOther(others.getText().toString().trim());
                    compModel.setDell(dell.getText().toString().trim());
                    compModel.setHp(hp.getText().toString().trim());
                    compModel.setStore_id(storeConfigModel.getUnique_store_id());
                    compModel.setStore_name(storeConfigModel.getStoreName());
                    compModel.setPromoter_id(storeConfigModel.getPromoter_id());
                    compModel.setPromoter_name(storeConfigModel.getPromoter_name());
                    String date =  simpleDateFormat.format(new Date()).trim();
                    databaseReference.child("comp_reporting").child(date).child(storeConfigModel.getUnique_store_id()).setValue(compModel);
                    finish();
                }
            }
        });


    }
}
