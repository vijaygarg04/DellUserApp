package com.example.shivam.delluserapp;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.SchemeModel;
import com.example.shivam.delluserapp.Fragments.SchemesFragment;

public class ShowSchemeActivity extends AppCompatActivity implements SchemesFragment.OnListFragmentInteractionListener{
MaterialDialog materialDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scheme);
        SchemesFragment schemesFragment = new SchemesFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,schemesFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onListFragmentInteraction(SchemeModel item) {
        new MaterialDialog.Builder(this)
                .title(item.getTitle())
                .content(item.getDescription())
                .positiveText("OK")
                .show();
    }
}
