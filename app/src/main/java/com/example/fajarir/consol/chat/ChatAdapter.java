package com.example.fajarir.consol.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fajarir.consol.Contact;
import com.example.fajarir.consol.R;
import java.util.List;

/**
 * Created by fajarir on 8/6/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    public List<Contact> getChats() {
        return mContacts;
    }

    // Store a member variable for the chat
    private List<Contact> mContacts;
    // Store the context for easy access
    private Context mContext;
    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ChatAdapter(List<Contact> mContacts, Context mContext) {
        this.mContacts = mContacts;
        this.mContext = mContext;
    }

    public void setData(List<Contact> contactList) {
        this.mContacts = contactList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public OnClickListener onClickListener;
        public TextView itemName;

        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            itemName= (TextView)itemView.findViewById(R.id.tv_chatroom_name);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (onClickListener != null){
                onClickListener.onClick(getAdapterPosition());
            }
        }
    }
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(v,onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);
// Set item views based on your views and data model
        TextView textView = holder.itemName;
        textView.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        if (mContacts == null) {
            return 0;
        }
        return mContacts.size();
    }

    public interface OnClickListener{
        void onClick(int adapterPosition);
    }

}
