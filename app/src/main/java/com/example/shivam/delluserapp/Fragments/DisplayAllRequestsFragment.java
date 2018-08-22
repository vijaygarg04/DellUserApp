package com.example.shivam.delluserapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.shivam.delluserapp.DataModels.DisplayModel;
import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.IssuesActivity;
import com.example.shivam.delluserapp.R;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayAllRequestsFragment extends Fragment {

    private int mColumnCount = 1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<DisplayModel> displayModels;
    MaterialDialog materialDialog;
    View view;
    Context context;
    TinyDB tinyDB;
    StoreConfigModel s;
    private OnListFragmentInteractionListener mListener;

    public DisplayAllRequestsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        displayModels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_display_list, container, false);
        materialDialog = new MaterialDialog.Builder(context)
                .title("Important Information")
                .content("Please Wait")
                .progress(true, 0)
                .progressIndeterminateStyle(true).build();
        materialDialog.show();
        databaseReference.child("display_request").child(s.getUnique_store_id()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
        DisplayModel dm = ds.getValue(DisplayModel.class);
        // Shows all requests and results in interactive fashion.
        if (s.getUnique_store_id().equals(dm.getStore_id())) {
            displayModels.add(dm);
            Log.e("My Tag : ", dm.getModel_number());
        }}
        if (displayModels.size()==0){
        materialDialog.dismiss();
        new MaterialDialog.Builder(context)
                .title("INFORMATION")
                .content("NO Requests Found....")
                .positiveText("OK")
                .show();

        //Toast.makeText(context,"No Data Found for this user",Toast.LENGTH_LONG).show();
    }else {
            materialDialog.dismiss();
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                RecyclerView recyclerView = (RecyclerView) view;
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }
                recyclerView.setAdapter(new AllDisplayAdapter(displayModels, mListener));
            }
        }

}else {

    materialDialog.dismiss();
    new MaterialDialog.Builder(context)
            .title("INFORMATION")
            .content("NO Display Data Found....")
            .positiveText("OK")
            .show();
    //Toast.makeText(context,"NO DATA FOUND",Toast.LENGTH_LONG).show();
}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new MaterialDialog.Builder(context)
                        .title("INFORMATION")
                        .content("DATABASE ERROR, TRY AGAIN LATER...This may occur due to slow or low network connectivity. Make sure you are in network range and try again ")
                        .positiveText("OK")
                        .show();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        tinyDB = new TinyDB(context);
        this.context= context;
        s = tinyDB.getObject(StaticConstants.config_object_key, StoreConfigModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DisplayModel item);
    }
}
