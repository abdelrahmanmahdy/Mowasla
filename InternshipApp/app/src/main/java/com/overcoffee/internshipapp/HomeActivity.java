package com.overcoffee.internshipapp;


import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, passwordET;
    TextInputLayout emailTIL,passwordTIL;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = (EditText) findViewById(R.id.email_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        loginBtn = (Button) findViewById(R.id.login_btn);
        emailTIL= (TextInputLayout) findViewById(R.id.email_et_layout);
        passwordTIL= (TextInputLayout) findViewById(R.id.password_et_layout);

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String emailInput = email.getText().toString();
                String password = passwordET.getText().toString();
                emailTIL.setErrorEnabled(false);
                passwordTIL.setErrorEnabled(false);
                if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    emailTIL.setErrorEnabled(true);
                    emailTIL.setError("Wrong email format, Try again!");
                }
                if (password.length() < 8) {
                    passwordTIL.setErrorEnabled(true);
                    passwordTIL.setError("Password too short !!!");
                }
                break;
            default:
        }
    }
}
