package com.example.shivam.delluserapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NavigatorActivity extends AppCompatActivity {
    Button passbook_button,transfer_device,scan_barcode_button,schemes_button, comp_report,issues_button,search_button,display_devices_button,logout_btn;
    TextView name_text,id_text;
    StoreConfigModel storeConfigModel;
    String final_pass_date ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        transfer_device = (Button)findViewById(R.id.transfer_device_navigator_button);
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


                show_date_dialoge();
                //startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("promoterinfo").child(storeConfigModel.getPromoter_id()).child("logged_in").setValue(false);
                Intent intent = new Intent(NavigatorActivity.this,LoginActivity.class);
                tinyDB.clear();
                startActivity(intent);
                finish();
            }
        });
        transfer_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigatorActivity.this,TransferActivity.class);

                if (ContextCompat.checkSelfPermission(NavigatorActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(NavigatorActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 1);
                }
                startActivity(intent);
            }
        });
    }

    public void show_date_dialoge(){
        final Calendar c = Calendar.getInstance();
      int  mYear = c.get(Calendar.YEAR);
       int  mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if((monthOfYear+1)<10 && dayOfMonth<10){

                            final_pass_date =  String.valueOf(year)+ "0"+String.valueOf(monthOfYear + 1) + "0"+String.valueOf(dayOfMonth) ;
                        }
                        else if ((monthOfYear+1)<10 ){
                            final_pass_date =  String.valueOf(year)+ "0"+String.valueOf(monthOfYear + 1) +String.valueOf(dayOfMonth) ;
                        }
                        else if (dayOfMonth<10){
                            final_pass_date =  String.valueOf(year)+ String.valueOf(monthOfYear + 1) + "0"+String.valueOf(dayOfMonth) ;
                        }
                        else {

                            final_pass_date =  String.valueOf(year)+ String.valueOf(monthOfYear + 1) + String.valueOf(dayOfMonth) ;

                        }
                       // Toast.makeText(NavigatorActivity.this,final_pass_date,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(NavigatorActivity.this,PassbookActivity.class);
                        intent.putExtra("date",final_pass_date);
                        startActivity(intent);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


}
