package com.example.shivam.delluserapp.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.StoreConfigModel;
import com.example.shivam.delluserapp.DataModels.chat_model;
import com.example.shivam.delluserapp.Fragments.ChatFragment.OnListFragmentInteractionListener;
import com.example.shivam.delluserapp.R;
import com.example.shivam.delluserapp.utils.StaticConstants;
import com.example.shivam.delluserapp.utils.TinyDB;

import java.util.List;
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final List<chat_model> mValues;
    private final OnListFragmentInteractionListener mListener;
    private static final int CHAT_END = 1;
    StoreConfigModel storeConfigModel;
    private static final int CHAT_START = 2;
    TinyDB tinyDB;

    public ChatAdapter(List<chat_model> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
     tinyDB = new TinyDB(context);
        mListener = listener;
        storeConfigModel = tinyDB.getObject(StaticConstants.config_object_key,StoreConfigModel.class);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == CHAT_END) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_end, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_start, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {

        if (mValues.get(position).getSent_by_id().equals(storeConfigModel.getPromoter_id())) {
            return CHAT_END;
        }

        return CHAT_START;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.message.setText(mValues.get(position).getMessage());
        holder.sender_name.setText(mValues.get(position).getSent_by_name() );

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView sender_name;
        public final TextView message;
        public chat_model mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sender_name = (TextView) view.findViewById(R.id.sender_name);
            message = (TextView) view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + message.getText() + "'";
        }
    }
}
