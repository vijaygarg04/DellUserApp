package com.example.shivam.delluserapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.delluserapp.DataModels.DisplayModel;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MakeDisplayRequestActivity extends AppCompatActivity {
    public static EditText editText;
    TinyDB tinyDB;
    Button submit_button,scan_barcode;
    FirebaseDatabase firebaseDatabase;
    StoreConfigModel storeConfigModel;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_display_request);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        tinyDB = new TinyDB(MakeDisplayRequestActivity.this);
        editText = (EditText)findViewById(R.id.input_service_text);
        submit_button = (Button)findViewById(R.id.submit_button_display);
        scan_barcode = (Button)findViewById(R.id.display_scan_button);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        if (ContextCompat.checkSelfPermission(MakeDisplayRequestActivity.this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MakeDisplayRequestActivity.this,
                    new String[]{android.Manifest.permission.CAMERA}, 1);

        }

        scan_barcode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ContextCompat.checkSelfPermission(MakeDisplayRequestActivity.this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(MakeDisplayRequestActivity.this,ScanActivity.class);
                        intent.putExtra("activity_name","display");
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MakeDisplayRequestActivity.this,"Permissions Not Granted",Toast.LENGTH_LONG).show();
                    }

                }
            });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String service_tag =  editText.getText().toString().trim();
                if (service_tag.length()>0){
                    //Check whether that tag is sold out or not,and then set its display variables values
                    databaseReference.child("msa").child(service_tag).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("service_tag")){
                                MainProduct mainProduct  = dataSnapshot.getValue(MainProduct.class);
                                if (!mainProduct.isDisplay_request()
                                    && mainProduct.getDisplay_request_result().equals("default")
                                    && !mainProduct.isStore_sell_out_date_set()
                                    && mainProduct.isStore_sell_in_date_set()
                                    && mainProduct.getStore_id().equals(storeConfigModel.getUnique_store_id())){
                                    mainProduct.setDisplay_request(true);
                                    mainProduct.setDisplay_request_result("pending");
                                    DisplayModel dm = new DisplayModel();
                                    dm.setIs_sold_out(false);
                                    dm.setModel_number(mainProduct.getModel_number());
                                    dm.setService_tag(service_tag);
                                    dm.setStore_id(storeConfigModel.getUnique_store_id());
                                    dm.setRequest_status(true);
                                    dm.setRequest_result("pending");
                                    dm.setStore_name(storeConfigModel.getStoreName());
                                    databaseReference.child("display_request").child(storeConfigModel.getUnique_store_id()).child(service_tag).setValue(dm);
                                    databaseReference.child("msa").child(service_tag).setValue(mainProduct);
                                    Toast.makeText(MakeDisplayRequestActivity.this,"Value Successfully Updated",Toast.LENGTH_LONG).show();
                                    finish();
                                    /*    tinyDB.putObject(StaticConstants.temp_show_info_object_key,mainProduct);
                                    Intent intent = new Intent(MakeDisplayRequestActivity.this,ShowInformationActivity.class);
                                    startActivity(intent);
                                */}
                                else
                                    {
                                    Toast.makeText(MakeDisplayRequestActivity.this,"This device cannot be requested",Toast.LENGTH_LONG).show();
                                }


                                editText.setText("");
                                 }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MakeDisplayRequestActivity.this,"DATA doesn't exist in firebase",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
}
//TODO : Update the display data in main product list(By Vijay)
