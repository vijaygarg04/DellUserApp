package com.example.shivam.delluserapp.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.Fragments.SellOutFragment.SellOUTListener;
import com.example.shivam.delluserapp.R;

import java.util.List;

public class SellOutAdapter extends RecyclerView.Adapter<SellOutAdapter.ViewHolder> {

    private final List<MainProduct> mValues;
    private final SellOUTListener mListener;

    public SellOutAdapter(List<MainProduct> items, SellOUTListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sell_out_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.model_number.setText("Model Number: "+mValues.get(position).getModel_number());
        holder.service_tag.setText("Service Tag: "+mValues.get(position).getService_tag());
        holder.sell_in_date.setText("IN: "+myDateFormatter(mValues.get(position).getStore_sell_in_date()));
        holder.sell_out_date.setText("OUT: "+myDateFormatter(mValues.get(position).getStore_sell_out_date()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.sellOUTInteraction(holder.mItem);
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
        public final TextView sell_out_date;
        public final TextView model_number;
        public final TextView service_tag;
        public MainProduct mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            sell_in_date = (TextView) view.findViewById(R.id.sell_in_date);
            sell_out_date = (TextView)view.findViewById(R.id.sell_out_date);
            model_number = (TextView) view.findViewById(R.id.model_number);
            service_tag = (TextView)view.findViewById(R.id.service_tag);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + service_tag.getText() + "'";
        }
    }
    public String myDateFormatter(String date){

        String a = date.substring(0,2) +"/" +date.substring(2,4) + "/"+ date.substring(4,8);

        return a;
    }
}
