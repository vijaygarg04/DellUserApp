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

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.DataModels.chat_model;
import com.example.shivam.delluserapp.R;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    List<chat_model> chat_models ;
    TinyDB tinyDB;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StoreConfigModel s ;
    ChatAdapter chatAdapter;
    View view;
    Context context;
    private OnListFragmentInteractionListener mListener;
    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chat_models = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_chat_list, container, false);
        s = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
           recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            chatAdapter = new ChatAdapter(chat_models, mListener,context);
            recyclerView.setAdapter(chatAdapter);
        }
        databaseReference.child("chat_room").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    try {

                        chat_model model = dataSnapshot.getValue(chat_model.class);
                        chat_models.add(model);
                        recyclerView.scrollToPosition(chat_models.size() - 1);
                        chatAdapter.notifyItemInserted(chat_models.size() - 1);
                    } catch (Exception ex) {
                        Log.e("Error From chat", ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Set the adapter

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        tinyDB = new TinyDB(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(chat_model item);
    }
}
