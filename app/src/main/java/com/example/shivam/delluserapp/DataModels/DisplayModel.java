package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 25/4/18.
 */

public class DisplayModel {
    boolean is_sold_out = false;
    String model_number = "default";
    String request_result = "accept";
    boolean request_status = false;
    String service_tag = "default";
    String store_id = "default";
    String store_name = "default";

    public boolean isIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(boolean is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getModel_number() {
        return model_number;
    }

    public void setModel_number(String model_number) {
        this.model_number = model_number;
    }

    public String getRequest_result() {
        return request_result;
    }

    public void setRequest_result(String request_result) {
        this.request_result = request_result;
    }

    public boolean isRequest_status() {
        return request_status;
    }

    public void setRequest_status(boolean request_status) {
        this.request_status = request_status;
    }

    public String getService_tag() {
        return service_tag;
    }

    public void setService_tag(String service_tag) {
        this.service_tag = service_tag;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public DisplayModel() {
    }
}
