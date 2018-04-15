package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 5/4/18.
 */

public class CompModel {
    public String dell ;
    public String hp ;
    public String lenovo ;
    public String acer ;
    public String other ;
    public String storename;
    public String getAcer() {
        return acer;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


    public void setStorename(String storename) {
        this.storename = storename;
    }

    public void setAcer(String acer) {
        this.acer = acer;
    }



    public String getDell() {
        return dell;
    }

    public void setDell(String dell) {
        this.dell = dell;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getLenovo() {
        return lenovo;
    }

    public void setLenovo(String lenovo) {
        this.lenovo = lenovo;
    }




    public CompModel() {
    }
}
