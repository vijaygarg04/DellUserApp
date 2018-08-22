package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowInformationActivity extends AppCompatActivity {
FirebaseDatabase firebaseDatabase;
MaterialDialog materialDialog;
TextView service_tag_text,modelNumber,msa_name,display_text,config_text,bundle_code,store;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        service_tag_text =(TextView)findViewById(R.id.service_tag_show_info);
        modelNumber = (TextView)findViewById(R.id.model_number_show_info);
        msa_name = (TextView)findViewById(R.id.msa_name_show_info);
        display_text=(TextView)findViewById(R.id.display_show_info);
        config_text = (TextView)findViewById(R.id.configuration_show_info);
        bundle_code = (TextView)findViewById(R.id.bundle_code_show_info);
        store =(TextView)findViewById(R.id.store_name_show_info);
        Intent intent = getIntent();
        String service_tag = intent.getStringExtra("service_tag");
        materialDialog = new MaterialDialog.Builder(ShowInformationActivity.this)
                .title("Important Information")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        materialDialog.show();
        databaseReference.child("msa").child(service_tag).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    MainProduct mainProduct = dataSnapshot.getValue(MainProduct.class);
                    service_tag_text.setText(mainProduct.getService_tag());
                    modelNumber.setText(mainProduct.getModel_number());
                    msa_name.setText(mainProduct.getMsa_name());
                    display_text.setText(mainProduct.getDisplay_request_result());
                    config_text.setText(mainProduct.getConfiguration());
                    bundle_code.setText(mainProduct.getBundle_code());
                    store.setText(mainProduct.getStore_name()+" (ID:"+mainProduct.getStore_id()+" )");

                    materialDialog.dismiss();


                }
                else {
                    Toast.makeText(ShowInformationActivity.this,"THIS PRODUCT IS NOT IN DATABASE",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
