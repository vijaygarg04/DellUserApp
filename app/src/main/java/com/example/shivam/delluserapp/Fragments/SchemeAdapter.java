package com.example.shivam.delluserapp.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shivam.delluserapp.DataModels.SchemeModel;
import com.example.shivam.delluserapp.Fragments.SchemesFragment.OnListFragmentInteractionListener;
import com.example.shivam.delluserapp.R;

import java.util.List;
public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {

    private final List<SchemeModel> mValues;
    Context context;
    private final OnListFragmentInteractionListener mListener;

    public SchemeAdapter(List<SchemeModel> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_scheme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.image.setText(mValues.get(position).id);
        holder.title.setText(mValues.get(position).getTitle());
        Glide.with(context)
                .load(mValues.get(position).getImageurl())
                .into(holder.image);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final ImageView image;
        public final TextView title;
        public SchemeModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}
