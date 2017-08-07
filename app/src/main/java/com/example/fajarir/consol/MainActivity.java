package com.example.fajarir.consol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fajarir.consol.data.DataManager;
import com.qiscus.sdk.Qiscus;
import com.qiscus.sdk.data.model.QiscusAccount;

import java.io.IOException;

import retrofit2.HttpException;


public class MainActivity extends AppCompatActivity {
    public TextView name, email, pass;
    private ProgressDialog mProgressDialog;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = new DataManager(this);
        checkUserIsExist();
        initialize();
    }

    private void checkUserIsExist() {
        if(!dataManager.getUserProfile().isEmpty()){
            startActivity(new Intent(this, TabActivity.class));
        }
    }


    private void initialize() {
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        pass = (TextView) findViewById(R.id.password);


    }

    public void logindsn(View view) {
        String namadsn = name.getText().toString();
        String emaildsn = email.getText().toString();
        String passdsn = pass.getText().toString();
        if (namadsn.isEmpty()) {
            name.setError("Please insert your name!");
            name.requestFocus();
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emaildsn).matches()) {
            email.setError("Please insert a valid email!");
            email.requestFocus();
        } else if (passdsn.isEmpty()) {
            pass.setError("Please insert your user key!");
            pass.requestFocus();
        } else {
            showLoading();
            Qiscus.setUser(emaildsn, passdsn)
                    .withUsername(namadsn)
                    .save(new Qiscus.SetUserListener() {
                        @Override
                        public void onSuccess(QiscusAccount qiscusAccount) {
                            dataManager.setUserProfile(emaildsn);
                            startActivity(new Intent(MainActivity.this, TabActivity.class));
                            dismissLoading();
                            finish();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            if (throwable instanceof HttpException) { //Error response from server
                                HttpException e = (HttpException) throwable;
                                try {
                                    String errorMessage = e.response().errorBody().string();
                                     Log.e(String.valueOf(this), errorMessage);
                                    showError(errorMessage);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } else if (throwable instanceof IOException) { //Error from network
                                showError("Can not connect to qiscus server!");
                            } else { //Unknown error
                                showError("Unexpected error!");
                            }
                            dismissLoading();
                        }
                    });
        }

    }

    public void loginmhs(View view) {
        String namamhs = name.getText().toString();
        String emailmhs = email.getText().toString();
        String passmhs = pass.getText().toString();
        if (namamhs.isEmpty()) {
            name.setError("Please insert your name!");
            name.requestFocus();
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailmhs).matches()) {
            email.setError("Please insert a valid email!");
            email.requestFocus();
        } else if (passmhs.isEmpty()) {
            pass.setError("Please insert your user key!");
            pass.requestFocus();
        } else {
            showLoading();
            Qiscus.setUser(emailmhs, passmhs)
                    .withUsername(namamhs)
                    .save(new Qiscus.SetUserListener() {
                        @Override
                        public void onSuccess(QiscusAccount qiscusAccount) {
                            startActivity(new Intent(MainActivity.this, TabActivity.class));
                            dismissLoading();
                            finish();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            if (throwable instanceof HttpException) { //Error response from server
                                HttpException e = (HttpException) throwable;
                                try {
                                    String errorMessage = e.response().errorBody().string();
                                    // Log.e(this, errorMessage);
                                    showError(errorMessage);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            } else if (throwable instanceof IOException) { //Error from network
                                showError("Can not connect to qiscus server!");
                            } else { //Unknown error
                                showError("Unexpected error!");
                            }
                            dismissLoading();
                        }
                    });
        }

    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void dismissLoading() {
        mProgressDialog.dismiss();
    }

    public static void revertCustomChatConfig() {
        Qiscus.getChatConfig()
                .setSendButtonIcon(R.drawable.ic_qiscus_send)
                .setShowAttachmentPanelIcon(R.drawable.ic_qiscus_attach);
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }
}
