package com.example.shivam.delluserapp.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.R;

import java.util.List;

/**
 * Created by shivam on 18/4/18.
 */

public class SearchResultViewHolder  extends RecyclerView.ViewHolder{

    TextView store_name_text_view,store_id_text_view,sell_in_date_text_view;
    public final View mView;
    public SearchResultViewHolder(final View itemView,List<MainProduct> mainProducts) {
        super(itemView);
        mView  = itemView;

        store_name_text_view = (TextView)itemView.findViewById(R.id.text_view_store_name);
        store_id_text_view = (TextView)itemView.findViewById(R.id.text_view_store_id);
        sell_in_date_text_view =(TextView)itemView.findViewById(R.id.text_view_sell_in_date);

    }
}
