package com.example.shivam.delluserapp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shivam.delluserapp.DataModels.MainProduct;
import com.example.shivam.delluserapp.R;
import com.example.shivam.delluserapp.ShowInformationActivity;

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
    public void onBindViewHolder(final SearchResultViewHolder holder, final int position) {
    holder.store_id_text_view.setText("Store :"+mainProducts.get(position).getStore_name()+" (ID: "+mainProducts.get(position).getStore_id()+" )");
    holder.store_name_text_view.setText("Config: "+mainProducts.get(position).getConfiguration());
    holder.sell_in_date_text_view.setText("Sell In Date:"+myDateFormatter(mainProducts.get(position).getStore_sell_in_date())+"(Model No:"+mainProducts.get(position).getModel_number()+")");
    holder.mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ShowInformationActivity.class);
            intent.putExtra("service_tag",mainProducts.get(position).getService_tag());
            context.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return mainProducts.size();
    }
    public String myDateFormatter(String date){

        String a=date;
        if (a.equals("default")){
            return a;
        }
        else {
            a = date.substring(6,8) +"/" +date.substring(4,6) + "/"+ date.substring(0,4);
            return a;
        }



    }
}
