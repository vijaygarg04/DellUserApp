package com.example.shivam.delluserapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmTransferActivity extends AppCompatActivity {
    TinyDB tinyDB ;
    TextView service_tag,model_no,config,display;
    Button confirm;
    FirebaseDatabase firebaseDatabase;
    MaterialDialog materialDialog;
    DatabaseReference databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_transfer);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference();
        tinyDB = new TinyDB(ConfirmTransferActivity.this);
        service_tag = (TextView)findViewById(R.id.service_tag_transfer);
        model_no = (TextView)findViewById(R.id.model_number_transfer);
        config = (TextView)findViewById(R.id.config_transfer);
        display = (TextView)findViewById(R.id.display_status_transfer);
        confirm = (Button)findViewById(R.id.confirm_transfer);
        final MainProduct mainProduct  = tinyDB.getObject("transfer_pass_object", MainProduct.class);
        service_tag.append(mainProduct.getService_tag());
        model_no.append(mainProduct.getModel_number());
        config.append(mainProduct.getConfiguration());
        if(mainProduct.getDisplay_request_result().equals("default") || mainProduct.getDisplay_request_result().equals("pending")){
            display.append(" Not IN Display");
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            MainProduct mainProduct1 =  new MainProduct(mainProduct.getService_tag()
                    ,mainProduct.getMsa_name()
                    ,mainProduct.isMsa_date_set()
                    ,mainProduct.getMsa_date()
                    ,mainProduct.getModel_number()
                    ,mainProduct.getBundle_code()
                    ,mainProduct.getConfiguration());
                materialDialog = new MaterialDialog.Builder(ConfirmTransferActivity.this)
                        .title("Updating Data")
                        .content("Please Don't Press Back..")
                        .canceledOnTouchOutside(false)
                        .progress(true, 0)
                        .progressIndeterminateStyle(true).build();
                materialDialog.show();

                databaseReference1.child("msa")
                        .child(mainProduct.getService_tag())
                        .setValue(mainProduct1,new DatabaseReference.CompletionListener(){
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Log.e("My Log: ",mainProduct.getStore_sell_in_date()+"  "+ mainProduct.getService_tag()+" "+mainProduct.getStore_id());
                        databaseReference1
                                .child("sell_in")
                                .child(mainProduct.getStore_id())
                                .child(mainProduct.getStore_sell_in_date())
                                .child(mainProduct.getService_tag())
                                .setValue(null, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        materialDialog.dismiss();
                               // Toast.makeText(ConfirmTransferActivity.this,"In This Block",Toast.LENGTH_LONG).show();
                                new MaterialDialog.Builder(ConfirmTransferActivity.this)
                                        .title("Information")
                                        .content("Data Updated")
                                        .positiveText("Ok")
                                        .canceledOnTouchOutside(false)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                finish();
                                            }
                                        }).show();
                            }
                        });
                    }
                });

            }
        });






    }
}
