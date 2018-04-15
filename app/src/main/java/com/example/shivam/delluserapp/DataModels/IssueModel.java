package com.example.shivam.delluserapp.DataModels;

/**
 * Created by shivam on 13/4/18.
 */
//TODO : CAN BE INCLUDED IN LATER VERSIONS OF THE APPLICATION
public class IssueModel {
    public String name_of_issue;
    public String description_of_issue;
    public String opening_date_of_issue="default";
    public String closing_date_of_issue ="default";
    public boolean status_of_issue = true;//True --> Open, False --> Closed
    public String chat_room_key;

    public String getName_of_issue() {
        return name_of_issue;
    }

    public void setName_of_issue(String name_of_issue) {
        this.name_of_issue = name_of_issue;
    }

    public String getDescription_of_issue() {
        return description_of_issue;
    }

    public void setDescription_of_issue(String description_of_issue) {
        this.description_of_issue = description_of_issue;
    }

    public String getOpening_date_of_issue() {
        return opening_date_of_issue;
    }

    public void setOpening_date_of_issue(String opening_date_of_issue) {
        this.opening_date_of_issue = opening_date_of_issue;
    }

    public String getClosing_date_of_issue() {
        return closing_date_of_issue;
    }

    public void setClosing_date_of_issue(String closing_date_of_issue) {
        this.closing_date_of_issue = closing_date_of_issue;
    }

    public boolean isStatus_of_issue() {
        return status_of_issue;
    }

    public void setStatus_of_issue(boolean status_of_issue) {
        this.status_of_issue = status_of_issue;
    }

    public String getChat_room_key() {
        return chat_room_key;
    }

    public void setChat_room_key(String chat_room_key) {
        this.chat_room_key = chat_room_key;
    }

    public IssueModel() {
    }
}
