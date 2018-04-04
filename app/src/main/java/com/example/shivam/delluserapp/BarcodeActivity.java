package com.example.shivam.delluserapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarcodeActivity extends AppCompatActivity {
    public static EditText editText;
    TinyDB tinyDB;
    Button submit_button,scan_barcode;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        tinyDB = new TinyDB(BarcodeActivity.this);
        editText = (EditText)findViewById(R.id.input_service_text);
        submit_button = (Button)findViewById(R.id.submit_button_barcode);
        scan_barcode = (Button)findViewById(R.id.barcode_scan_button);

        final Intent intent = getIntent();
        if (ContextCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BarcodeActivity.this,
                    new String[]{Manifest.permission.CAMERA}, 1);

        }
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final String service_tag =  editText.getText().toString().trim();
               if (service_tag.length()>0){
                   //Check whether that tag is available in present product list, If yes, add in sell In or sell Out, If not, prompt the user to make it
                   databaseReference.child("msa").child(service_tag).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                          if (dataSnapshot.hasChild("service_tag")){
                              MainProduct mainProduct  = dataSnapshot.getValue(MainProduct.class);
                              Intent intent1 = new Intent(BarcodeActivity.this, SetSellingDetailsActivity.class);
                              tinyDB.putObject("selling_object",mainProduct);
                              startActivity(intent1);
                              editText.setText("");
                              Toast.makeText(BarcodeActivity.this,"update DATA NOW",Toast.LENGTH_LONG).show();
                            }
                            else
                          {
                              Toast.makeText(BarcodeActivity.this,"DATA doesn't exist in firebase",Toast.LENGTH_LONG).show();
                              Intent intent1 = new Intent(BarcodeActivity.this,AddProductAsUserActivity.class);
                              intent1.putExtra("service_tag",service_tag);
                              startActivity(intent1);
                          }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(BarcodeActivity.this,"DATA doesn't exist in firebase",Toast.LENGTH_LONG).show();
                       }
                   });

               }
            }
        });
        scan_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(BarcodeActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(BarcodeActivity.this,ScanActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(BarcodeActivity.this,"Permissions Not Granted",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
