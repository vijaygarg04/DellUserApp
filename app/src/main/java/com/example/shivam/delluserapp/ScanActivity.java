package com.example.shivam.delluserapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shivam.delluserapp.utils.TinyDB;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity {

    private ZXingScannerView zXingScannerView;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(ScanActivity.this);
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
            setContentView(R.layout.activity_barcode);
            BarcodeActivity.editText.setText(result_code);
            onBackPressed();
        }
    }
}
