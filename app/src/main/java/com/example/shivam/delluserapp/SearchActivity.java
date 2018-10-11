package com.example.shivam.delluserapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.utils.SearchResultAdapter;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.miguelcatalan.materialsearchview.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity{
    MaterialSearchView searchView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StoreConfigModel storeConfigModel;
    TinyDB tinyDB;
    List<String> keys;
    SearchResultAdapter searchAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<MainProduct> mainProducts;
    MaterialDialog materialDialog;
    
    HashMap<String,ArrayList<MainProduct>> myhash=new HashMap<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        tinyDB = new TinyDB(SearchActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        keys = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(this);
        mainProducts = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference();
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        searchView.setVoiceSearch(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                //Do some magic
                keys.clear();
                mainProducts.clear();
                Log.e("First -->",query);
                materialDialog = new MaterialDialog.Builder(SearchActivity.this)
                        .title("Loading Information..")
                        .content("Please Wait..")
                        .progress(true, 0)
                        .progressIndeterminateStyle(true).build();
                materialDialog.show();
                databaseReference.child("sell_in").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            for (DataSnapshot ps : ds.getChildren()){
                                for (DataSnapshot fs : ps.getChildren()){
                                    if (fs.getValue(String.class).contains(query)){
                                        String key=fs.getKey();
                                        keys.add(key);
                                }

                                }
                            }

                        }
                        //Adapter would be set here
                        for (String s : keys){
                            databaseReference.child("msa").child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                 MainProduct mainProduct = dataSnapshot.getValue(MainProduct.class);
                                 if (!mainProduct.isStore_sell_out_date_set() && mainProduct.getDisplay_request_result().equals("default")){
                                     mainProducts.add(mainProduct);
                                 }
                                 searchAdapter = new SearchResultAdapter(mainProducts,SearchActivity.this);
                                 recyclerView.setAdapter(searchAdapter);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        
                        for(MainProduct mainProduct:mainProducts){
                        String storename=mainProduct.getStoreName();
                            if(myhash.containsKey(storename)==false){
                                myhash.put(storename,new ArrayList<MainProduct>);
                            }
                            Arraylist<MainProduct> myarrylist=myhash.get(storename);
                            myarrlist.add(mainProduct);
                        }
                        mainProducts=new ArrayList<>();
                        Arraylist<String> storekeys=new ArrayList<>(myhash.keyset());
                        for(String storekey:storekeys){
                        ArrayList<MainProduct> mp=myhash.get(storekey);
                            if(mp.size()>=1){
                                mainProducts.add(mp.get(0));
                            }
                        }
                        materialDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                Log.e("Second-->",newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic

            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
