package com.example.shivam.delluserapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.DataModels.chat_model;
import com.example.shivam.delluserapp.Fragments.ChatFragment;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IssuesActivity extends AppCompatActivity implements ChatFragment.OnListFragmentInteractionListener{
    Button add_issue;
    EditText editText;
    TinyDB tinyDB;
    FirebaseDatabase firebaseDatabase;
    StoreConfigModel s;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        editText = (EditText)findViewById(R.id.message_edit_text);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =  firebaseDatabase.getReference();
        tinyDB = new TinyDB(IssuesActivity.this);
        ChatFragment chatFragment= new ChatFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.frame,chatFragment);
        fragmentTransaction.commit();
        s= tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        add_issue = (Button)findViewById(R.id.send_button);
        add_issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length()>0){
                    chat_model c = new chat_model();
                    c.setMessage(editText.getText().toString());
                    c.setSent_by_admin(false);
                    c.setSeny_by_name(s.getPromoter_id());
                    c.setSent_by_id(s.getPromoter_id());
                    databaseReference.child("chat_room").push().setValue(c);
                    editText.setText("");
                }
            }
        });

    }

    @Override
    public void onListFragmentInteraction(chat_model item) {
        Toast.makeText(IssuesActivity.this,item.getMessage(),Toast.LENGTH_LONG).show();
    }
}
