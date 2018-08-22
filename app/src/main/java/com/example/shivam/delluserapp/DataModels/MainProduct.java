package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 20/3/18.
 */
//This is the object that would be used to push to firebase and also recieve data in some cases.
public class MainProduct {

//1.)This is the main key
public String service_tag = "default";
//2.)This is the name of the MSA which uploads products. It is uploaded by Admin. It can be empty also.
public String msa_name = "default";
//3,4,5.)This is the name of the store which recieves the product from distributor. It can't be empty.
public boolean store_name_set = false;
public String store_id= "default";
public String store_name = "default";
//6,7.)This is the date when MSA enters the product, i.e. when admin enters the product into the app.
public boolean msa_date_set = false;
public String msa_date = "default";
//8,9.)This is the data when store recieves the product from warehouse or MSA
public String store_sell_in_date = "default";
public boolean store_sell_in_date_set = false;
//10,11.)This is the date whem the product is sold out from the store to the consumer.
public String store_sell_out_date = "default";
public boolean store_sell_out_date_set = false;
//12.)This is the model number of the product, would be set by the admin while entering the product into database.
public String model_number= "default";
//13.)Bundle Code
public String bundle_code= "default";
//14.)This is (promoter id)
public String sold_by_promoter_id= "default";
//15.) this is the promoter's name
public String sold_by_promoter_name= "default";
//16,17.) Used for managing display models
public String display_request_result = "default";
public boolean display_request = false;
//18.) This is Configuration
public String configuration = "default";

    public MainProduct(String service_tag, String msa_name, boolean msa_date_set, String msa_date, String model_number, String bundle_code, String configuration) {
        this.service_tag = service_tag;
        this.msa_name = msa_name;
        this.msa_date_set = msa_date_set;
        this.msa_date = msa_date;
        this.model_number = model_number;
        this.bundle_code = bundle_code;
        this.configuration = configuration;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getSold_by_promoter_id() {
        return sold_by_promoter_id;
    }

    public void setSold_by_promoter_id(String sold_by_promoter_id) {
        this.sold_by_promoter_id = sold_by_promoter_id;
    }

    public String getDisplay_request_result() {
        return display_request_result;
    }

    public void setDisplay_request_result(String display_request_result) {
        this.display_request_result = display_request_result;
    }

    public boolean isDisplay_request() {
        return display_request;
    }

    public void setDisplay_request(boolean display_request) {
        this.display_request = display_request;
    }

    public String getService_tag() {
        return service_tag;
    }

    public void setService_tag(String service_tag) {
        this.service_tag = service_tag;
    }

    public String getMsa_name() {
        return msa_name;
    }

    public void setMsa_name(String msa_name) {
        this.msa_name = msa_name;
    }

    public boolean isStore_name_set() {
        return store_name_set;
    }

    public void setStore_name_set(boolean store_name_set) {
        this.store_name_set = store_name_set;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public boolean isMsa_date_set() {
        return msa_date_set;
    }

    public void setMsa_date_set(boolean msa_date_set) {
        this.msa_date_set = msa_date_set;
    }

    public String getMsa_date() {
        return msa_date;
    }

    public void setMsa_date(String msa_date) {
        this.msa_date = msa_date;
    }

    public String getStore_sell_in_date() {
        return store_sell_in_date;
    }

    public void setStore_sell_in_date(String store_sell_in_date) {
        this.store_sell_in_date = store_sell_in_date;
    }

    public boolean isStore_sell_in_date_set() {
        return store_sell_in_date_set;
    }

    public void setStore_sell_in_date_set(boolean store_sell_in_date_set) {
        this.store_sell_in_date_set = store_sell_in_date_set;
    }

    public String getStore_sell_out_date() {
        return store_sell_out_date;
    }

    public void setStore_sell_out_date(String store_sell_out_date) {
        this.store_sell_out_date = store_sell_out_date;
    }

    public boolean isStore_sell_out_date_set() {
        return store_sell_out_date_set;
    }

    public void setStore_sell_out_date_set(boolean store_sell_out_date_set) {
        this.store_sell_out_date_set = store_sell_out_date_set;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public String getBundle_code() {
        return bundle_code;
    }

    public void setBundle_code(String bundle_code) {
        this.bundle_code = bundle_code;
    }

    public String getSold_by_promoter_name() {
        return sold_by_promoter_name;
    }

    public void setSold_by_promoter_name(String sold_by_promoter_name) {
        this.sold_by_promoter_name = sold_by_promoter_name;
    }

    public MainProduct() {
        //For firebase

    }

    public MainProduct(String service_tag, String msa_name, boolean store_name_set, String store_name, boolean msa_date_set, String msa_date, String store_sell_in_date, boolean store_sell_in_date_set, String store_sell_out_date, boolean store_sell_out_date_set, String model_number, String bundle_code, String sold_by_promoter_name,String configuration) {
        this.service_tag = service_tag;
        this.msa_name = msa_name;
        this.store_name_set = store_name_set;
        this.store_name = store_name;
        this.msa_date_set = msa_date_set;
        this.msa_date = msa_date;
        this.store_sell_in_date = store_sell_in_date;
        this.store_sell_in_date_set = store_sell_in_date_set;
        this.store_sell_out_date = store_sell_out_date;
        this.store_sell_out_date_set = store_sell_out_date_set;
        this.model_number = model_number;
        this.bundle_code = bundle_code;
        this.sold_by_promoter_name = sold_by_promoter_name;
        this.configuration = configuration;
    }
    //TODO : Modify this product object according to the data required.
}
