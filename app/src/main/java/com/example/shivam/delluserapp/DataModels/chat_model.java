package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 13/4/18.
 */

public class chat_model {
    //TODO : Later on can be converted into proper chat, using chat library while submitting if required.
    public String sent_by_id;

    public String getSent_by_id() {
        return sent_by_id;
    }

    public void setSent_by_id(String sent_by_id) {
        this.sent_by_id = sent_by_id;
    }

    //The text message
    public String message;
    //name of promoter comes here
    public String seny_by_name;
    //Tells whether sent by admin or not
    public boolean sent_by_admin;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeny_by_name() {
        return seny_by_name;
    }

    public void setSeny_by_name(String seny_by_name) {
        this.seny_by_name = seny_by_name;
    }

    public boolean isSent_by_admin() {
        return sent_by_admin;
    }

    public void setSent_by_admin(boolean sent_by_admin) {
        this.sent_by_admin = sent_by_admin;
    }

    public chat_model() {
    }
}
