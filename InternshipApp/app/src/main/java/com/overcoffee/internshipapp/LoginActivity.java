package com.overcoffee.internshipapp;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, passwordET;
    TextInputLayout emailTIL, passwordTIL;
    Button loginBtn, regBtn;
    boolean emailGood, pwGood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        loginBtn = (Button) findViewById(R.id.login_btn);
        regBtn = (Button) findViewById(R.id.register_btn);
        emailTIL = (TextInputLayout) findViewById(R.id.email_et_layout);
        passwordTIL = (TextInputLayout) findViewById(R.id.password_et_layout);
        emailGood = pwGood = false;

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String emailIn = email.getText().toString();
                emailTIL.setErrorEnabled(false);
                if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
                    emailTIL.setErrorEnabled(true);
                    emailTIL.setError("Wrong email format!");
                    emailGood = false;
                } else emailGood = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = passwordET.getText().toString();
                passwordTIL.setErrorEnabled(false);
                if (password.length() < 8) {
                    passwordTIL.setErrorEnabled(true);
                    passwordTIL.setError("Password too short!");
                    pwGood = false;
                } else pwGood = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Intent intent = getIntent();
        email.setText(intent.getStringExtra("email"));
        passwordET.setText(intent.getStringExtra("pw"));

        loginBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                if (emailGood && pwGood) {
                    Backendless.UserService.login(email.getText().toString(),
                            passwordET.getText().toString(),
                            new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser response) {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("email", response.getEmail());
                                    intent.putExtra("name", response.getProperty("name").toString());
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    TextView textView= (TextView) findViewById(R.id.credentials_msg);
                                    textView.setText("WRONG CREDENTIALS!");
                                }
                            }, true);
                }
                break;
            case R.id.register_btn:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("pw", passwordET.getText().toString());
                startActivity(intent);
                finish();
                break;
            default:
        }
    }


}
