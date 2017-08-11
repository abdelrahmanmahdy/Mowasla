package com.overcoffee.internshipapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.API_KEY);
        String currentUserObjectId = Backendless.UserService.loggedInUser();
        Log.d("MOWASLA", currentUserObjectId);
        if (!currentUserObjectId.isEmpty()) {
            Log.d("MOWASLA", "finding user id "+currentUserObjectId);
            Backendless.UserService.findById(currentUserObjectId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    Backendless.UserService.setCurrentUser(response);
                    new CountDownTimer(1500, 500) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            Intent intent;
                            intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }.start();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        } else {
            Log.d("MOWASLA","no current user");
            new CountDownTimer(1500, 500) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }.start();
        }


    }
}
