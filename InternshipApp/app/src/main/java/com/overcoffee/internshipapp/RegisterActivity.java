package com.overcoffee.internshipapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
    Spinner country, timezones;
    boolean emailGOOD, passwordGOOD, confirmedGOOD, mobileGOOD;
    Uri pic;

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
        timezones = (Spinner) findViewById(R.id.time_zone_spinner);

        emailGOOD = passwordGOOD = confirmedGOOD = mobileGOOD = false;

        fillTimeZoneSpinner();

        Intent intent = getIntent();

        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String emailIn = emailET.getText().toString();
                emailTIL.setErrorEnabled(false);
                if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()) {
                    emailTIL.setErrorEnabled(true);
                    emailTIL.setError("Wrong email format!");
                    emailGOOD = false;
                } else emailGOOD = true;
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
                pwTIL.setErrorEnabled(false);
                if (password.length() < 8) {
                    pwTIL.setErrorEnabled(true);
                    pwTIL.setError("Password too short!");
                    passwordGOOD = false;
                } else passwordGOOD = true;
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
                confirmedPwTIL.setErrorEnabled(false);
                if (!confirmedPwET.getText().toString().equals(passwordET.getText().toString())) {
                    confirmedPwTIL.setErrorEnabled(true);
                    confirmedPwTIL.setError("password does not match!");
                    confirmedGOOD = false;
                } else confirmedGOOD = true;
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
                mobileTIL.setErrorEnabled(false);
                if (!Patterns.PHONE.matcher(mobileET.getText().toString()).matches()) {
                    mobileTIL.setErrorEnabled(true);
                    mobileTIL.setError("wrong format!");
                    mobileGOOD = false;
                } else mobileGOOD = true;
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
            pic = data.getData();
            pp.setImageURI(pic);
        }
        if(requestCode==GET_FROM_CAMERA && requestCode==Activity.RESULT_OK){
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
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
                        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,GET_FROM_CAMERA);
                    }
                });
                alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GET_FROM_GALLERY);
                    }
                });
                AlertDialog dialog=alertDialog.create();
                dialog.show();
                break;
            //TODO: next button logic
            case R.id.reg_next_btn:
                if (emailGOOD && passwordGOOD && confirmedGOOD && mobileGOOD) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("name", nameET.getText().toString());
                    intent.putExtra("email", emailET.getText().toString());
                    intent.putExtra("mobile", mobileET.getText().toString());
                    intent.putExtra("country", country.getSelectedItem().toString());
                    intent.putExtra("timezone", timezones.getSelectedItem().toString());
                    if (pic != null) {
                        intent.putExtra("pp", pic);
                    }
                    startActivity(intent);
                    finish();
                }
                break;
            default:
        }
    }

    private void fillTimeZoneSpinner() {
        String[] ids = TimeZone.getAvailableIDs();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ids);
        timezones.setAdapter(adapter);
    }
}
