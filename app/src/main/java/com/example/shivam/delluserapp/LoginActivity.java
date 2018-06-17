package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    EditText email,pass;
    //This is the activity when user logins for the first time or opens up the app for the first time
    Button submit;
    DatabaseReference databaseReference;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.submit_btn);
        tinyDB = new TinyDB(LoginActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_text = email.getText().toString().trim();
                final String pass_text = pass.getText().toString().trim();
                if (email_text.length()!=0 && pass_text.length()!=0){
                    databaseReference.child("promoterinfo").child(email_text).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && dataSnapshot.getValue()!=null) {
                                if (dataSnapshot.child("is_active").getValue(Boolean.class)) {
                                    Log.e("1","1");
                                if (pass_text.equals(dataSnapshot.child("password").getValue(String.class))) {
                                    if (!dataSnapshot.child("logged_in").getValue(Boolean.class)) {
                                        databaseReference.child("promoterinfo").child(email_text).child("logged_in").setValue(true);
                                        Log.e("2","2");
                                    }

                                    StoreConfigModel storeConfigModel = new StoreConfigModel();
                                    storeConfigModel.setPromoter_name(dataSnapshot.child("promoter_name").getValue(String.class));
                                    storeConfigModel.setMobile_number(dataSnapshot.child("promoter_contact").getValue(String.class));
                                    storeConfigModel.setPromoter_id(dataSnapshot.child("promoter_id").getValue(String.class));
                                    storeConfigModel.setDate_of_joining(dataSnapshot.child("date_of_joining").getValue(String.class));
                                    storeConfigModel.setStoreName(dataSnapshot.child("store_name").getValue(String.class));
                                    storeConfigModel.setUnique_store_id(dataSnapshot.child("store_id").getValue(String.class));
                                    storeConfigModel.setIs_logged_in(true);
                                    tinyDB.putObject(StaticConstants.config_object_key, storeConfigModel);
                                    tinyDB.putBoolean(StaticConstants.is_config_object_created, true);
                                    Intent intent = new Intent(LoginActivity.this,NavigatorActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.password_error, Toast.LENGTH_LONG).show();
                                }
                            }
                            }
                            else {
                                Toast.makeText(LoginActivity.this,"This username doesn't exist",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                Toast.makeText(LoginActivity.this,"Can't Be Empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
