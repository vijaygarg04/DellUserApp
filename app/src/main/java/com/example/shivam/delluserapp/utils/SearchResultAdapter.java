package com.example.shivam.delluserapp.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.R;

import java.util.List;

/**
 * Created by shivam on 17/4/18.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {
    public List<MainProduct> mainProducts;
    public Context context;
    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchResultViewHolder searchResultViewHolder = null;
        LayoutInflater li= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = li.inflate(R.layout.custom_search_layout,parent,false);
        searchResultViewHolder = new SearchResultViewHolder(layoutView,mainProducts);
        return searchResultViewHolder;
    }

    public SearchResultAdapter(List<MainProduct> mainProducts,Context context) {
        this.mainProducts = mainProducts;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
    holder.store_id_text_view.append(mainProducts.get(position).getStore_id());
    holder.store_name_text_view.append(mainProducts.get(position).getStore_name());
    holder.sell_in_date_text_view.append(myDateFormatter(mainProducts.get(position).getStore_sell_in_date()));
    }

    @Override
    public int getItemCount() {
        return mainProducts.size();
    }
    public String myDateFormatter(String date){

        String a = date.substring(0,2) +"/" +date.substring(2,4) + "/"+ date.substring(4,8);

        return a;
    }
}
