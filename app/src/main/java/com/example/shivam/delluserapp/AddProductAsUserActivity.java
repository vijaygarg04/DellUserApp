package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddProductAsUserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button set_date,submit_button;
    TextView service_tag_text, store_name_id;
    MainProduct mainProduct;
    FirebaseDatabase firebaseDatabase;
    SimpleDateFormat simpleDateFormat;
    DatabaseReference databaseReference;
    EditText model_number, bundle_code,configuration;
    MaterialDialog materialDialog;
    boolean is_date_set=  false;
    TinyDB tinyDB;
    String temp_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_as_user);
        Intent intent =  getIntent();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        model_number = (EditText)findViewById(R.id.model_number_edit_text_add_user);
        bundle_code = (EditText)findViewById(R.id.bundle_code_edit_text_add_user);
        store_name_id = (TextView)findViewById(R.id.store_name_text_add_user);
        tinyDB = new TinyDB(AddProductAsUserActivity.this);
        mainProduct = new MainProduct();
        configuration = (EditText)findViewById(R.id.configuration_edit_text_add_user);
        final String service_tag = intent.getStringExtra("service_tag");
        Log.e("Service_tag",service_tag);
        materialDialog = new MaterialDialog.Builder(AddProductAsUserActivity.this)
                .title("Updating Data")
                .content("Don't Press Back")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();

        set_date = (Button)findViewById(R.id.set_date_button_add_user);
        submit_button = (Button)findViewById(R.id.submit_button_add_user);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        service_tag_text = (TextView)findViewById(R.id.service_tag_text_add_user);
        final StoreConfigModel storeConfigModel= tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
        Log.e("Details :","ID; "+storeConfigModel.getUnique_store_id()+ " Name:"+storeConfigModel.getStoreName());
        service_tag_text.append(service_tag);
        store_name_id.append(storeConfigModel.getStoreName()+"("+storeConfigModel.getUnique_store_id()+")");
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_date_set && model_number.getText().toString().trim().length()>0 && configuration.getText().toString().trim().length()>0){
                    if (bundle_code.getText().toString().length()>0){
                        mainProduct.setBundle_code(bundle_code.getText().toString().trim());
                    }
                    mainProduct.setModel_number(model_number.getText().toString().trim());
                    mainProduct.setService_tag(service_tag);
                    mainProduct.setConfiguration(configuration.getText().toString().trim());
                    mainProduct.setStore_id(storeConfigModel.getUnique_store_id());
                    mainProduct.setStore_name(storeConfigModel.getStoreName());
                    mainProduct.setStore_name_set(true);
                    mainProduct.setStore_sell_in_date(temp_date);
                    mainProduct.setStore_sell_in_date_set(true);
                    materialDialog.show();




                    databaseReference
                            .child("sell_in")
                            .child(storeConfigModel.getUnique_store_id())
                            .child(temp_date)
                            .child(mainProduct.getService_tag())
                            .setValue(mainProduct.getModel_number(), new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError!=null){
                                        new MaterialDialog.Builder(AddProductAsUserActivity.this)
                                                .title("Information")
                                                .content("Couldn't Update Database, Check Internet Connection and try again later..")
                                                .positiveText("OK")
                                                .show();
                                        Toast.makeText(AddProductAsUserActivity.this,"",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(AddProductAsUserActivity.this,"Sell In data Updated.",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                    databaseReference
                            .child("msa")
                            .child(service_tag)
                            .setValue(mainProduct, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    materialDialog.dismiss();
                                    if (databaseError!=null){
                                        new MaterialDialog.Builder(AddProductAsUserActivity.this)
                                                .title("Information")
                                                .content("Couldn't Update Database, Check Internet Connection and try again later..")
                                                .positiveText("OK")
                                                .show();
                                        Toast.makeText(AddProductAsUserActivity.this,"Data Could Not Be Uploaded",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(AddProductAsUserActivity.this,"MSA Data Updated Successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                            });

                }
                else {
                    Toast.makeText(AddProductAsUserActivity.this,"Enter all the fields..",Toast.LENGTH_LONG).show();
                }
            }
        });
        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                showDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), R.style.DatePickerSpinner);
                String date =  simpleDateFormat.format(new Date()).trim();
                Log.e("My Tag 1:",date);
            }
        });

    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(AddProductAsUserActivity.this)
                .callback(AddProductAsUserActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }


    public String myDateFormatter(String date){

        String a = date.substring(6,8) +"/" +date.substring(4,6) + "/"+ date.substring(0,4);

        return a;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        temp_date = simpleDateFormat.format(calendar.getTime()).replace("-","");
        set_date.setText(myDateFormatter(temp_date));
        is_date_set = true;
    }
}
