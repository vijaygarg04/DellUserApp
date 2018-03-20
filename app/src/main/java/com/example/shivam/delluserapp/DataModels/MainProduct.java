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
//3,4.)This is the name of the store which recieves the product from distributor. It can't be empty.
public boolean store_name_set = false;
public String store_name = "default";
//5,6.)This is the date when MSA enters the product, i.e. when admin enters the product into the app.
public boolean msa_date_set = false;
public String msa_date = "default";
//7,8.)This is the data when store recieves the product from warehouse or MSA
public String store_sell_in_date = "default";
public boolean store_sell_in_date_set = false;
//9,10.)This is the date whem the product is sold out from the store to the consumer.
public String store_sell_out_date = "default";
public boolean store_sell_out_date_set = false;
//11.)This is the model number of the product, would be set by the admin while entering the product into database.
public String model_number= "default";
//12.)Bundle Code
public String bundle_code= "default";
//13.)The person who sells the product. To be set in user app.
public String sold_by_promoter_name= "default";

//TODO : Modify this product object according to the data required.
}
