package com.example.fajarir.consol;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by fajarir on 7/29/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<Contact> mContacts;
    // Store the context for easy access
    private Context mContext;
    private OnClickListener onClickListener;

    public List<Contact> getContacts() {
        return mContacts;
    }
    public ContactAdapter(List<Contact> mContacts, Context mContext) {
        this.mContacts = mContacts;
        this.mContext = mContext;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemName;
        public OnClickListener onClickListener;

        public ViewHolder(View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
            itemName = (TextView) itemView.findViewById(R.id.tv_name);
        }


        @Override
        public void onClick(View view) {
            if (onClickListener != null){
                onClickListener.onClick(getAdapterPosition());
            }
        }
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(v,onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Contact contact = mContacts.get(position);
// Set item views based on your views and data model
        TextView textView = holder.itemName;
        textView.setText(contact.getName());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public interface OnClickListener{
        void onClick(int adapterPosition);
    }

}
