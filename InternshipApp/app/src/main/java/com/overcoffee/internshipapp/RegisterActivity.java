package com.overcoffee.internshipapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
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
    EditText nameET, emailET, passwordET, confirmedPwET, mobileET, goldET, silverET;
    TextInputLayout emailTIL, pwTIL, confirmedPwTIL, mobileTIL;
    Spinner country, timezones, fiqh;
    boolean emailGOOD, passwordGOOD, confirmedGOOD, mobileGOOD;
    Bitmap pic;

    public static final int GET_FROM_GALLERY = 3;

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
        goldET = (EditText) findViewById(R.id.gold_et);
        silverET = (EditText) findViewById(R.id.silver_et);
        //TEXT INPUT LAYOUTS
        emailTIL = (TextInputLayout) findViewById(R.id.email_reg_et_layout);
        pwTIL = (TextInputLayout) findViewById(R.id.pw_reg_et_layout);
        confirmedPwTIL = (TextInputLayout) findViewById(R.id.cpw_reg_et_layout);
        mobileTIL = (TextInputLayout) findViewById(R.id.mob_reg_et_layout);
        //SPINNERS
        country = (Spinner) findViewById(R.id.country_spinner);
        timezones = (Spinner) findViewById(R.id.time_zone_spinner);
        fiqh = (Spinner) findViewById(R.id.fiqh_spinner);

        emailGOOD = passwordGOOD = confirmedGOOD = mobileGOOD = false;
        pic = null;

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
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                pp.setImageBitmap(bitmap);
                pic = bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pp_btn:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
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
                    intent.putExtra("fiqh", fiqh.getSelectedItem().toString());
                    intent.putExtra("gold", goldET.getText().toString());
                    intent.putExtra("silver", silverET.getText().toString());
                    if (pic != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] bytes = stream.toByteArray();
                        intent.putExtra("pp", bytes);
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
