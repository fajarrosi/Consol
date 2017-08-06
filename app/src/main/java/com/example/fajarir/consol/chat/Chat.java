package com.example.fajarir.consol.chat;

import com.example.fajarir.consol.Contact;

import java.util.ArrayList;

/**
 * Created by fajarir on 8/6/2017.
 */

public class Chat {
    private String mName;
    private String mEmail;

    public Chat(String mName, String mEmail) {
        this.mName = mName;
        this.mEmail = mEmail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
    public static ArrayList<Chat> setChatList() {
        ArrayList<Chat> chats = new ArrayList<Chat>();
        chats.add(new Chat("Doni", "doni@gmail.com"));
        return chats;
    }
}
