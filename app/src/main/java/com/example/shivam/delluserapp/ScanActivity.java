package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shivam.delluserapp.utils.TinyDB;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity {

    private ZXingScannerView zXingScannerView;
    TinyDB tinyDB;
    String activity_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(ScanActivity.this);
        Intent intent = getIntent();
        activity_name = intent.getStringExtra("activity_name");
        zXingScannerView = new ZXingScannerView(this);
        zXingScannerView.setResultHandler(new ZxingScannerResultHandler());
        setContentView(zXingScannerView);
        zXingScannerView.startCamera();

    }

    @Override
    public void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();
    }
    @Override
    public void onResume(){
        super.onResume();
        zXingScannerView.startCamera();
    }

    class ZxingScannerResultHandler implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(com.google.zxing.Result result) {
            String result_code = result.getText();
            Toast.makeText(ScanActivity.this,result_code,Toast.LENGTH_LONG).show();
            if (activity_name.equals("barcode")){
                setContentView(R.layout.activity_barcode);
                BarcodeActivity.editText.setText(result_code);
                onBackPressed();
            }
            else if (activity_name.equals("display")){
                setContentView(R.layout.activity_make_display_request);
                MakeDisplayRequestActivity.editText.setText(result_code);
                onBackPressed();
            }
            else if (activity_name.equals("transfer")){
                setContentView(R.layout.activity_transfer);
                TransferActivity.edit_transfer.setText(result_code);
                onBackPressed();
            }

        }
    }
}
