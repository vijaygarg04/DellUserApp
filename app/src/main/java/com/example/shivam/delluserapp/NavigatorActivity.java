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

public class NavigatorActivity extends AppCompatActivity {
    Button passbook_button,scan_barcode_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        scan_barcode_button = (Button)findViewById(R.id.scan_barcode_button);
        passbook_button = (Button)findViewById(R.id.passbook_button);
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
