package com.example.fajarir.consol;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fajarir.consol.chat.Chat;
import com.example.fajarir.consol.chat.ChatAdapter;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.ui.adapter.OnItemClickListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.fajarir.consol.MainActivity.revertCustomChatConfig;

/**
 * Created by fajarir on 7/28/2017.
 */

public class ContactFragment extends Fragment {
    @BindView(R2.id.rv_contacts)RecyclerView rvContacts;
    private ContactAdapter adapter;
    private ChatAdapter adapter2;
    private Unbinder unbinder;
    ArrayList<Contact> contacts;
    ArrayList<Chat> chats;
    private ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(layoutManager);
        // Initialize contacts
        contacts = Contact.setContactsList();
        adapter = new ContactAdapter(contacts,getContext());
        rvContacts.setAdapter(adapter);
        adapter.setOnClickListener(adapterPosition -> {
                OpenChatWith(adapter.getContacts().get(adapterPosition));
        });
    }

    private void OpenChatWith(Contact contact) {
        showLoading();
        Qiscus.buildChatWith(contact.getEmail())
                .withTitle(contact.getName())
                .build(getContext(), new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivity(intent);
                        chats.add(new Chat(contact.getEmail(),contact.getName()));
                        adapter2.notifyDataSetChanged();
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) { //Error response from server
                            HttpException e = (HttpException) throwable;
                            try {
                                String errorMessage = e.response().errorBody().string();
                                //  Log.e(this, errorMessage);
                                showError(errorMessage);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else if (throwable instanceof IOException) { //Error from network
                            showError("Can not connect to qiscus server!");
                        } else { //Unknown error
                            showError("Unexpected error!");
                        }
                        throwable.printStackTrace();
                        dismissLoading();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
    private void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void dismissLoading() {
        mProgressDialog.dismiss();
    }
}
