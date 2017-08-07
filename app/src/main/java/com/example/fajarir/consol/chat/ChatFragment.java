package com.example.fajarir.consol.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fajarir.consol.Contact;
import com.example.fajarir.consol.R;
import com.example.fajarir.consol.R2;
import com.example.fajarir.consol.data.DataManager;
import com.qiscus.sdk.Qiscus;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.HttpException;

/**
 * Created by fajarir on 7/28/2017.
 */

public class ChatFragment extends Fragment {
    @BindView(R2.id.rv_chats)RecyclerView rvChats;
    private ChatAdapter adapter;
    private Unbinder unbinder;
    ArrayList<Contact> contacts;
    private ProgressDialog mProgressDialog;
    private DataManager dataManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvChats.setLayoutManager(layoutManager);
        // Initialize contacts
        dataManager = new DataManager(getContext());
//        contacts = Contact.setChatList();
        adapter = new ChatAdapter(dataManager.getListConsultation(), getContext());

        adapter.setOnClickListener(adapterPosition -> {
            OpenChatWith(adapter.getChats().get(adapterPosition));
        });
        rvChats.setAdapter(adapter);
    }

    private void OpenChatWith(Contact contact) {
        showLoading();
        Qiscus.buildChatWith(contact.getEmail())
                .withTitle(contact.getName())
                .build(getContext(), new Qiscus.ChatActivityBuilderListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivity(intent);
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


    public void notifyChangeData() {
        adapter.setData(dataManager.getListConsultation());
    }
}
