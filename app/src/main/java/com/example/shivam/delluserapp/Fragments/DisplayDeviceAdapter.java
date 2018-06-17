package com.example.shivam.delluserapp.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.DisplayModel;
import com.example.shivam.delluserapp.Fragments.DisplayDevicesFragment.OnListFragmentInteractionListener;
import com.example.shivam.delluserapp.R;

import java.util.List;

public class DisplayDeviceAdapter extends RecyclerView.Adapter<DisplayDeviceAdapter.ViewHolder> {

    private final List<DisplayModel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public DisplayDeviceAdapter(List<DisplayModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment__display_device_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.model_number.setText(mValues.get(position).getModel_number());
        holder.service_tag.setText(mValues.get(position).getService_tag());
        holder.sell_in_date.setText(mValues.get(position).getStore_name() + "ID  : "+ mValues.get(position).getStore_id());
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
        public final TextView sell_in_date;
        public final TextView model_number;
        public final TextView service_tag;
        public DisplayModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sell_in_date = (TextView) view.findViewById(R.id.sell_in_date_display);
            service_tag = (TextView) view.findViewById(R.id.service_tag_display);
            model_number = (TextView)view.findViewById(R.id.model_number_display);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + service_tag.getText() + "'";
        }
    }
}
