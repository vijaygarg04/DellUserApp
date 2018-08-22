package com.example.shivam.delluserapp.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.DisplayModel;
import com.example.shivam.delluserapp.Fragments.DisplayAllRequestsFragment.OnListFragmentInteractionListener;
import com.example.shivam.delluserapp.R;

import java.util.List;

public class AllDisplayAdapter extends RecyclerView.Adapter<AllDisplayAdapter.ViewHolder> {

    private final List<DisplayModel> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AllDisplayAdapter(List<DisplayModel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_all_display_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.model_number.setText("Model No.: "+mValues.get(position).getModel_number());
        holder.service_tag.setText("Service Tag: "+mValues.get(position).getService_tag());
        holder.sell_in_date.setText("Result : " + holder.mItem.getRequest_result());
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
            sell_in_date = (TextView) view.findViewById(R.id.sell_in_date_display_all);
            service_tag = (TextView) view.findViewById(R.id.service_tag_display_all);
            model_number = (TextView)view.findViewById(R.id.model_number_display_all);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + service_tag.getText() + "'";
        }

    }
}
