package com.example.fajarir.consol.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fajarir.consol.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.getType;

/**
 * Created by fajarir on 8/6/2017.
 */

public class DataManager {
    SharedPreferences sharedpreferences;
    Gson gson = new Gson();
    ArrayList<Contact>  listconsultation = new ArrayList<>();;
    boolean cek = false;

    public DataManager(Context context) {
        sharedpreferences = context.getSharedPreferences("contact5", context.MODE_PRIVATE);
       // listconsultation = new ArrayList<>();
    }

    public void setListConsultation(Contact contact){

        if(listconsultation !=null){
            if (listconsultation.size() == 0){
                listconsultation.add(contact);
                String contact2 = gson.toJson(listconsultation, new TypeToken<ArrayList<Contact>>(){}.getType());
                sharedpreferences.edit().putString("contact",contact2).apply();
            }
            else {
                for (int i = 0; i < listconsultation.size(); i++) {
                    if (listconsultation.get(i).getEmail() != contact.getEmail() ) {
                       cek = true;
                    } else {
                        cek = false;
                        break;
                    }
                }
                if (cek) {
                    listconsultation.add(contact);
                    String contact2 = gson.toJson(listconsultation, new TypeToken<ArrayList<Contact>>() {
                    }.getType());
                    sharedpreferences.edit().putString("contact", contact2).apply();
                }

            }

        }
    }


    public List<Contact> getListConsultation(){
        String contact4 = sharedpreferences.getString("contact","");
        List<Contact> contact3= gson.fromJson(contact4,new TypeToken<ArrayList<Contact>>(){}.getType());
        return contact3;
    }

    public  void setUserProfile(String email){
        sharedpreferences.edit().putString("email",email).apply();
    }

    public String getUserProfile(){
       return sharedpreferences.getString("email","");
    }



}
