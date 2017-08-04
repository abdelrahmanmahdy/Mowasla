package com.overcoffee.internshipapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton pp;
    Button next;
    EditText nameET, emailET, passwordET, confirmedPwET, mobileET;
    TextInputLayout emailTIL, pwTIL, confirmedPwTIL, mobileTIL;
    Spinner country;
    boolean emailGOOD, passwordGOOD, confirmedGOOD, mobileGOOD;

    public static final int GET_FROM_GALLERY = 3;
    public static final int GET_FROM_CAMERA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //BUTTONS
        pp = (ImageButton) findViewById(R.id.pp_btn);
        next = (Button) findViewById(R.id.reg_next_btn);
        //EDIT TEXTS
        nameET = (EditText) findViewById(R.id.username_et);
        emailET = (EditText) findViewById(R.id.email_reg_et);
        passwordET = (EditText) findViewById(R.id.pw_reg_et);
        confirmedPwET = (EditText) findViewById(R.id.cpw_reg_et);
        mobileET = (EditText) findViewById(R.id.mob_reg_et);
        //TEXT INPUT LAYOUTS
        emailTIL = (TextInputLayout) findViewById(R.id.email_reg_et_layout);
        pwTIL = (TextInputLayout) findViewById(R.id.pw_reg_et_layout);
        confirmedPwTIL = (TextInputLayout) findViewById(R.id.cpw_reg_et_layout);
        mobileTIL = (TextInputLayout) findViewById(R.id.mob_reg_et_layout);
        //SPINNERS
        country = (Spinner) findViewById(R.id.country_spinner);

        emailGOOD = passwordGOOD = confirmedGOOD = mobileGOOD = false;

        Intent intent = getIntent();

        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String emailIn = emailET.getText().toString();
                if(emailIn.isEmpty()){
                    emailGOOD=false;
                    emailTIL.setErrorEnabled(false);
                }else
                if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
                    emailTIL.setErrorEnabled(true);
                    emailTIL.setError("Wrong email format!");
                    emailGOOD = false;
                } else {
                    emailTIL.setErrorEnabled(false);
                    emailGOOD = true;
                }
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
                if(password.isEmpty()){
                    passwordGOOD=false;
                    pwTIL.setErrorEnabled(false);
                }else
                if (password.length() < 8) {
                    pwTIL.setErrorEnabled(true);
                    pwTIL.setError("Password too short!");
                    passwordGOOD = false;
                } else {
                    pwTIL.setErrorEnabled(false);
                    passwordGOOD = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        confirmedPwET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(confirmedPwET.getText().toString().isEmpty()){
                    confirmedGOOD=false;
                    confirmedPwTIL.setErrorEnabled(false);
                }else
                if (!confirmedPwET.getText().toString().equals(passwordET.getText().toString())) {
                    confirmedPwTIL.setErrorEnabled(true);
                    confirmedPwTIL.setError("password does not match!");
                    confirmedGOOD = false;
                } else {
                    confirmedPwTIL.setErrorEnabled(false);
                    confirmedGOOD = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mobileET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(mobileET.getText().toString().isEmpty()){
                    mobileGOOD=false;
                    mobileTIL.setErrorEnabled(false);
                }else
                if (!Patterns.PHONE.matcher(mobileET.getText().toString()).matches()) {
                    mobileTIL.setErrorEnabled(true);
                    mobileTIL.setError("wrong format!");
                    mobileGOOD = false;
                } else {
                    mobileTIL.setErrorEnabled(false);
                    mobileGOOD = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        emailET.setText(intent.getStringExtra("email"));
        passwordET.setText(intent.getStringExtra("pw"));


        pp.setOnClickListener(this);
        next.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("email", emailET.getText().toString());
        intent.putExtra("pw", passwordET.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            pp.setImageURI(data.getData());
        }
        if (requestCode == GET_FROM_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            pp.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pp_btn:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("choose picture from ..");
                alertDialog.setPositiveButton("camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, GET_FROM_CAMERA);
                    }
                });
                alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GET_FROM_GALLERY);
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                break;
            //TODO: next button logic
            case R.id.reg_next_btn:
                if (emailGOOD && passwordGOOD && confirmedGOOD && mobileGOOD) {
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(emailET.getText().toString());
                    user.setPassword(passwordET.getText().toString());
                    user.setProperty("name", nameET.getText().toString());
                    user.setProperty("mobile", mobileET.getText().toString());
                    user.setProperty("country",country.getSelectedItem().toString());
                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.putExtra("name", nameET.getText().toString());
                            intent.putExtra("email", emailET.getText().toString());
                            intent.putExtra("mobile", mobileET.getText().toString());
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            ((BitmapDrawable) pp.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG,100,stream);
                            byte[] bytes=stream.toByteArray();
                            intent.putExtra("pp", bytes);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(RegisterActivity.this, fault.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            default:
        }
    }
}
