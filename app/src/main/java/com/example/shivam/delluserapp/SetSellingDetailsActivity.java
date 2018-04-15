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

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
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

public class SetSellingDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    TinyDB tinyDB ;
    MainProduct mainProduct;
    Button date_sell_out_Button,sell_in,sell_out,submit_button,date_sell_in_button;
    FirebaseDatabase firebaseDatabase;
    String sell_in_date,sell_out_date;
    DatabaseReference databaseReference;
    SimpleDateFormat simpleDateFormat;
    public boolean sell_in_date_set = false,sell_out_date_set = false,can_sell_in_data_manipulated = true,can_sell_out_data_manipulated = true;
    public boolean is_sell_in_pressed = false,is_sell_out_pressed = false,temp = true;
    TextView service_tag_text_view,msa_name_text_view,bundle_code_text_view,model_name_text_view;
    EditText store_name_edit_text,promoter_name_edit_text;
    StoreConfigModel storeConfigModel;
    //TODO : Consider the case when a barcode is scanned by two different stores.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_selling_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        date_sell_in_button = (Button)findViewById(R.id.date_sell_in_button);
        submit_button = (Button)findViewById(R.id.submit_button);
        sell_in = (Button)findViewById(R.id.add_to_sell_in_button);
        sell_out = (Button)findViewById(R.id.add_to_sell_out_button);
        databaseReference = firebaseDatabase.getReference();
        tinyDB = new TinyDB( SetSellingDetailsActivity.this);
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
        mainProduct  = tinyDB.getObject("selling_object", MainProduct.class);
        sell_in_date = mainProduct.getStore_sell_in_date();
        sell_out_date = mainProduct.getStore_sell_out_date();
        service_tag_text_view = (TextView)findViewById(R.id.service_tag_text_view);
        msa_name_text_view = (TextView)findViewById(R.id.msa_name_text_view);
        bundle_code_text_view = (TextView)findViewById(R.id.bundle_code_text_view);
        model_name_text_view = (TextView)findViewById(R.id.model_number_text_view);
        store_name_edit_text = (EditText)findViewById(R.id.store_name_edit_text);
        promoter_name_edit_text = (EditText)findViewById(R.id.promoter_name_edit_text);
        bundle_code_text_view.append(mainProduct.getBundle_code());
        model_name_text_view.append(mainProduct.getModel_number());
        msa_name_text_view.append(mainProduct.getMsa_name());
        service_tag_text_view.append(mainProduct.getService_tag());
        date_sell_out_Button = (Button) findViewById(R.id.set_date_button);
        simpleDateFormat = new SimpleDateFormat("ddMMyyyy", Locale.US);
        if(!mainProduct.isStore_sell_in_date_set() || mainProduct.getStore_name().equals(storeConfigModel.getStoreName())){
        if (mainProduct.isStore_sell_out_date_set()){
            date_sell_out_Button.setText(myDateFormatter(mainProduct.getStore_sell_out_date()));
        }
        if (mainProduct.isStore_sell_in_date_set()){
            date_sell_in_button.setText(myDateFormatter(mainProduct.getStore_sell_in_date()));
        }
        date_sell_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (can_sell_in_data_manipulated ){
                    temp = true;
                    showDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), R.style.DatePickerSpinner);
                    String date =  simpleDateFormat.format(new Date()).trim();
                    Log.e("My Tag 1:",date);

                }
            }
        });
        date_sell_out_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                if (can_sell_out_data_manipulated ){
                showDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), R.style.DatePickerSpinner);
               String date =  simpleDateFormat.format(new Date()).trim();
               Log.e("My Tag 1:",date);
               temp = false;
            }
            }
        });
        if (!mainProduct.isStore_sell_in_date_set() && !mainProduct.isStore_sell_out_date_set()){
            //Product has arrived the store for the first time, thus all the details need to be set.
            sell_in.setBackgroundColor(getResources().getColor(R.color.button_color));
            sell_out.setBackgroundColor(getResources().getColor(R.color.button_color));
            store_name_edit_text.setText(storeConfigModel.getStoreName());
            promoter_name_edit_text.setText(storeConfigModel.getPromoter_name());
            store_name_edit_text.setEnabled(false);
            promoter_name_edit_text.setEnabled(false);
        }
        else if(mainProduct.isStore_sell_in_date_set() && !mainProduct.isStore_sell_out_date_set()){
            //Product had already arrived on the store and now can only be sold out
            date_sell_in_button.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            sell_in.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            sell_out.setBackgroundColor(getResources().getColor(R.color.button_color));
            promoter_name_edit_text.setText(mainProduct.getSold_by_promoter_name());
            store_name_edit_text.setText(mainProduct.getStore_name());
            store_name_edit_text.setEnabled(false);
            promoter_name_edit_text.setEnabled(false);
            can_sell_in_data_manipulated = false;
        }
        else if (mainProduct.isStore_sell_in_date_set() && mainProduct.isStore_sell_out_date_set()){
            sell_in.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            sell_out.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            can_sell_in_data_manipulated = false;
            can_sell_out_data_manipulated = false;
            promoter_name_edit_text.setText(mainProduct.getSold_by_promoter_name());
            store_name_edit_text.setText(mainProduct.getStore_name());
            promoter_name_edit_text.setEnabled(false);
            store_name_edit_text.setEnabled(false);
            date_sell_out_Button.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            date_sell_in_button.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
        }

        sell_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (can_sell_in_data_manipulated && !is_sell_in_pressed){
                    sell_in.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                    is_sell_in_pressed = true;
                }
                else if(can_sell_in_data_manipulated && is_sell_in_pressed){
                    sell_in.setBackgroundColor(getResources().getColor(R.color.button_color));
                    is_sell_in_pressed = false;
                }
            }
        });
        sell_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (can_sell_out_data_manipulated && !is_sell_out_pressed){
                    sell_out.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                    is_sell_out_pressed = true;
                }

                else if(can_sell_out_data_manipulated && is_sell_out_pressed){
                    sell_out.setBackgroundColor(getResources().getColor(R.color.button_color));
                    is_sell_out_pressed = false;
                }
            }
        });
    submit_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

                    if (can_sell_out_data_manipulated)
                    {
                        if (is_sell_in_pressed && is_sell_out_pressed && promoter_name_edit_text.getText().toString().trim().length()>0 &&
                                store_name_edit_text.getText().toString().trim().length()>0 &&
                                sell_in_date_set && sell_out_date_set){
                            mainProduct.setStore_sell_in_date_set(true);
                            mainProduct.setStore_sell_in_date(sell_in_date);
                            mainProduct.setSold_by_promoter_name(promoter_name_edit_text.getText().toString().trim());
                            mainProduct.setStore_name(store_name_edit_text.getText().toString().trim());
                            mainProduct.setStore_name_set(true);
                            mainProduct.setStore_sell_out_date_set(true);
                            mainProduct.setStore_sell_out_date(sell_out_date);
                            databaseReference.child("sell_in")
                                    .child(mainProduct.getStore_name())
                                    .child(sell_in_date)
                                    .child(mainProduct.getService_tag())
                                    .setValue(mainProduct.getModel_number());
                            databaseReference.child("sell_out")
                                    .child(mainProduct.getStore_name())
                                    .child(sell_out_date)
                                    .child(mainProduct.getService_tag())
                                    .setValue(mainProduct.getSold_by_promoter_name());
                            databaseReference.child("msa").child(mainProduct.getService_tag()).setValue(mainProduct);
                            Toast.makeText(SetSellingDetailsActivity.this,"Data Successfully Updated",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if (is_sell_in_pressed && !is_sell_out_pressed && promoter_name_edit_text.getText().toString().trim().length()>0 &&
                            store_name_edit_text.getText().toString().trim().length()>0 && sell_in_date_set){
                            mainProduct.setStore_sell_in_date_set(true);
                            mainProduct.setStore_sell_in_date(sell_in_date);
                            mainProduct.setSold_by_promoter_name(promoter_name_edit_text.getText().toString().trim());
                            mainProduct.setStore_name(store_name_edit_text.getText().toString().trim());
                            mainProduct.setStore_name_set(true);
                            databaseReference.child("sell_in")
                                    .child(mainProduct.getStore_name())
                                    .child(sell_in_date)
                                    .child(mainProduct.getService_tag())
                                    .setValue(mainProduct.getModel_number());
                            databaseReference.child("msa").child(mainProduct.getService_tag()).setValue(mainProduct);
                            Toast.makeText(SetSellingDetailsActivity.this,"Data Successfully Updated",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if (is_sell_out_pressed && !is_sell_in_pressed && !can_sell_in_data_manipulated && sell_out_date_set){
                            mainProduct.setStore_sell_out_date_set(true);
                            mainProduct.setStore_sell_out_date(sell_out_date);
                            databaseReference.child("sell_out")
                                    .child(mainProduct.getStore_name())
                                    .child(sell_out_date)
                                    .child(mainProduct.getService_tag())
                                    .setValue(mainProduct.getSold_by_promoter_name());

                            databaseReference.child("msa").child(mainProduct.getService_tag()).setValue(mainProduct);
                            Toast.makeText(SetSellingDetailsActivity.this,"Data Successfully Updated",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if (!is_sell_in_pressed && is_sell_out_pressed && can_sell_in_data_manipulated){
                            Toast.makeText(SetSellingDetailsActivity.this,"Can't Sell Out before Selling In",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SetSellingDetailsActivity.this,"Enter the data properly",Toast.LENGTH_LONG).show();

                        }
                    }



            else {
                Toast.makeText(SetSellingDetailsActivity.this,"This is object is sold out, data cannot be changed",Toast.LENGTH_LONG).show();
                    }
        }
    });

    }else{
            Toast.makeText(SetSellingDetailsActivity.this, "This Product is at another store, you can't change its details from this store",Toast.LENGTH_LONG).show();
            date_sell_in_button.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            sell_in.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            date_sell_out_Button.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            store_name_edit_text.setText(mainProduct.getStore_name());
            store_name_edit_text.setEnabled(false);
            promoter_name_edit_text.setEnabled(false);
            sell_out.setBackgroundColor(getResources().getColor(R.color.inactive_button_color));
            submit_button.setText("RETURN");
            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        String temp_date = simpleDateFormat.format(calendar.getTime());
        if (temp){
            date_sell_in_button.setText(myDateFormatter(temp_date));
            sell_in_date = temp_date;
            sell_in_date_set = true;
        } else {
            sell_out_date = temp_date;
            sell_out_date_set = true;
            date_sell_out_Button.setText(myDateFormatter(temp_date));
        }
    }

    @VisibleForTesting
    void showDate(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(SetSellingDetailsActivity.this)
                .callback(SetSellingDetailsActivity.this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    public String myDateFormatter(String date){

        String a = date.substring(0,2) +"/" +date.substring(2,4) + "/"+ date.substring(4,8);

        return a;
    }
}
