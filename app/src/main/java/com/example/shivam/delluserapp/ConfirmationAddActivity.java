package com.example.shivam.delluserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmationAddActivity extends AppCompatActivity {
    Button button;
    String service_tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_add);
        button = (Button)findViewById(R.id.continue_button);
        Intent intent = getIntent();
       service_tag = intent.getStringExtra("service_tag");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmationAddActivity.this,AddProductAsUserActivity.class);
                intent.putExtra("service_tag",service_tag);
                startActivity(intent);
                finish();
            }
        });
    }
}
