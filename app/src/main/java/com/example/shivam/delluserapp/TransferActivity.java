package com.example.shivam.delluserapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransferActivity extends AppCompatActivity {
Button submit,scan;
FirebaseDatabase database;
StoreConfigModel storeConfigModel;
DatabaseReference databaseReference;
TinyDB tinyDB;
public static EditText edit_transfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        database = FirebaseDatabase.getInstance();
        databaseReference =  database.getReference();
        scan = (Button)findViewById(R.id.scan_button_transfer);
        submit = (Button)findViewById(R.id.submit_button_transfer);
        edit_transfer = (EditText)findViewById(R.id.input_service_text_transfer);
        tinyDB = new TinyDB(TransferActivity.this);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(TransferActivity.this, android.Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(TransferActivity.this,ScanActivity.class);
                    intent.putExtra("activity_name","transfer");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(TransferActivity.this,"Permissions Not Granted",Toast.LENGTH_LONG).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_transfer.getText().toString().trim().length()>1){

                    databaseReference.child("msa")
                            .child(edit_transfer.getText().toString().trim())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            MainProduct mainProduct = dataSnapshot.getValue(MainProduct.class);
                            if (mainProduct.isStore_sell_in_date_set() && mainProduct.getStore_id().equals(storeConfigModel.getUnique_store_id()) && !mainProduct.isStore_sell_out_date_set() && !mainProduct.display_request_result.equals("accept") ){

                                tinyDB.putObject("transfer_pass_object",mainProduct);
                                Intent intent = new Intent(TransferActivity.this,ConfirmTransferActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                new MaterialDialog.Builder(TransferActivity.this)
                                        .title("INFORMATION")
                                        .content("You Cannot Transfer Products Which Are Already Sold Out Or Present In Display")
                                        .positiveText("OK")
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }



            }
        });
    }
}
