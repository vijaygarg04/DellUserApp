package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 24/3/18.
 */

public class StoreConfigModel {

    public String store_name = "default";
    //Can Be Used in further Development of Project as Name can Have Space Problems
    public String unique_store_id = "default";
    public String mobile_number= "default";
    public String location_of_store= "default";
    public String date_of_joining = "default";
    public String promoter_name= "default";
    public String promoter_id = "default";
    public String chat_room= "default";
    public boolean is_logged_in = false;

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getPromoter_id() {
        return promoter_id;
    }

    public void setPromoter_id(String promoter_id) {
        this.promoter_id = promoter_id;
    }

    public boolean isIs_logged_in() {
        return is_logged_in;
    }

    public void setIs_logged_in(boolean is_logged_in) {
        this.is_logged_in = is_logged_in;
    }

    public StoreConfigModel() {
    }

    public String getStoreName() {
        return store_name;
    }

    public void setStoreName(String name) {
        this.store_name = name;
    }

    public String getUnique_store_id() {
        return unique_store_id;
    }

    public void setUnique_store_id(String unique_store_id) {
        this.unique_store_id = unique_store_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getLocation_of_store() {
        return location_of_store;
    }

    public void setLocation_of_store(String location_of_store) {
        this.location_of_store = location_of_store;
    }

    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }

    public String getChat_room() {
        return chat_room;
    }

    public void setChat_room(String chat_room) {
        this.chat_room = chat_room;
    }
}
