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
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.delluserapp.utils.TinyDB;

public class BarcodeActivity extends AppCompatActivity {
    public static EditText editText;
    TinyDB tinyDB;
    Button submit_button,scan_barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        tinyDB = new TinyDB(BarcodeActivity.this);
        editText = (EditText)findViewById(R.id.input_service_text);
        submit_button = (Button)findViewById(R.id.submit_button_barcode);
        scan_barcode = (Button)findViewById(R.id.barcode_scan_button);
        final Intent intent = getIntent();
        if (ContextCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BarcodeActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 1);

        }
        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(BarcodeActivity.this,ScanActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(BarcodeActivity.this,"Permissions Not Granted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
