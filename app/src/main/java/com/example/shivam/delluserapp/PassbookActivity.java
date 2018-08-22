package com.example.shivam.delluserapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.Fragments.SellInFragment;
import com.example.shivam.delluserapp.Fragments.SellOutFragment;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class PassbookActivity extends AppCompatActivity implements SellInFragment.SellINListener,SellOutFragment.SellOUTListener{
//TODO : Show Fragments for Sell In Sell OUT and reports with a filter provided in each of them to sort according to the date.
    private TextView mTextMessage;
    TinyDB tinyDB ;
    StoreConfigModel storeConfigModel;
    String date_input;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    SellInFragment sellInFragment = new SellInFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,sellInFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    SellOutFragment sellOutFragment = new SellOutFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction().replace(R.id.frame,sellOutFragment);
                    fragmentTransaction2.commit();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);
        tinyDB = new TinyDB(PassbookActivity.this);
        Intent intent = getIntent();
        date_input = intent.getStringExtra("date");
        tinyDB.putString("date_string",date_input);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
        SellInFragment sellInFragment = new SellInFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,sellInFragment);
        fragmentTransaction.commit();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(MainProduct item) {
        Intent intent = new Intent(PassbookActivity.this,ShowInformationActivity.class);
        intent.putExtra("service_tag",item.getService_tag());
        startActivity(intent);


    }

    @Override
    public void sellOUTInteraction(MainProduct item) {

        Intent intent = new Intent(PassbookActivity.this,ShowInformationActivity.class);
        intent.putExtra("service_tag",item.getService_tag());
        startActivity(intent);

    }

}
