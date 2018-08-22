package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.DisplayModel;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.Fragments.DisplayAllRequestsFragment;
import com.example.shivam.delluserapp.Fragments.DisplayDevicesFragment;
import com.example.shivam.delluserapp.Fragments.SellInFragment;
import com.example.shivam.delluserapp.Fragments.SellOutFragment;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

public class DisplayActivity extends AppCompatActivity implements DisplayDevicesFragment.OnListFragmentInteractionListener
        ,DisplayAllRequestsFragment.OnListFragmentInteractionListener{
    private TextView mTextMessage;
    TinyDB tinyDB ;
    Button button;
    StoreConfigModel storeConfigModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        tinyDB = new TinyDB(DisplayActivity.this);
        button = (Button)findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayActivity.this,MakeDisplayRequestActivity.class);
                startActivity(intent);
            }
        });
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
        mTextMessage = (TextView) findViewById(R.id.display_message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.display_devices);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.display_devices:
                    mTextMessage.setText(R.string.title_home);
                    DisplayDevicesFragment displayDevicesFragment = new DisplayDevicesFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,displayDevicesFragment);
                    fragmentTransaction.commit();
                    mTextMessage.setText("Display Devices");
                    return true;
                case R.id.display_all:
                    DisplayAllRequestsFragment displayAllRequestsFragment = new DisplayAllRequestsFragment();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction().replace(R.id.frame,displayAllRequestsFragment);
                    fragmentTransaction2.commit();
                    mTextMessage.setText("All Requestes");
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onListFragmentInteraction(DisplayModel item) {
        Intent intent = new Intent(DisplayActivity.this,ShowInformationActivity.class);
        intent.putExtra("service_tag",item.getService_tag());
        startActivity(intent);

    }
}
