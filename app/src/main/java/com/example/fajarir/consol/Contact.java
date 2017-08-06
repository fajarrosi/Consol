package com.example.fajarir.consol;

import java.util.ArrayList;

/**
 * Created by fajarir on 8/6/2017.
 */

public class Contact {
    private String mName;
    private String mEmail;

    public Contact(String mName, String mEmail) {
        this.mName = mName;
        this.mEmail = mEmail;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public static ArrayList<Contact> setContactsList() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        contacts.add(new Contact("Adi", "adi@gmail.com"));
        contacts.add(new Contact("Budi", "budi@gmail.com"));
        contacts.add(new Contact("Cris", "cris@gmail.com"));

        return contacts;
    }
}
