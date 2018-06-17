package com.example.shivam.delluserapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class NavigatorActivity extends AppCompatActivity {
    Button passbook_button,scan_barcode_button,schemes_button, comp_report,issues_button,search_button,display_devices_button,logout_btn;
    TextView name_text,id_text;
    StoreConfigModel storeConfigModel;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        logout_btn = (Button)findViewById(R.id.logout_btn_navigator);
        name_text = (TextView)findViewById(R.id.name_navigator_text);
        id_text = (TextView)findViewById(R.id.id_navigator_text);
        comp_report = (Button)findViewById(R.id.report_competetion_button);
        search_button= (Button)findViewById(R.id.search_button);
        display_devices_button = (Button)findViewById(R.id.display_devices_button);
        tinyDB = new TinyDB(NavigatorActivity.this);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.clear();
                Intent intent = new Intent(NavigatorActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
        name_text.setText("Name: "+storeConfigModel.getPromoter_name()+ "("+storeConfigModel.getPromoter_id()+")");
        id_text.setText("Store: "+storeConfigModel.getStoreName()+"("+storeConfigModel.getUnique_store_id()+")");
        display_devices_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,DisplayActivity.class);
                startActivity(intent);
            }
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        issues_button = (Button)findViewById(R.id.issues_button);
        issues_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,IssuesActivity.class);
                startActivity(intent);
            }
        });
        comp_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,ReportCompetetionActivity.class);
                startActivity(intent);
            }
        });
        scan_barcode_button = (Button)findViewById(R.id.scan_barcode_button);
        passbook_button = (Button)findViewById(R.id.passbook_button);
        schemes_button = (Button)findViewById(R.id.schemes_button);
        schemes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,ShowSchemeActivity.class);
                startActivity(intent);
            }
        });
        scan_barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,BarcodeActivity.class);

                if (ContextCompat.checkSelfPermission(NavigatorActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(NavigatorActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
                startActivity(intent);
            }
        });
        passbook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NavigatorActivity.this,PassbookActivity.class);
                startActivity(intent);
            }
        });
    }
}
