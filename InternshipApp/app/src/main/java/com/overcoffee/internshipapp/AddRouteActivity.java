package com.overcoffee.internshipapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.overcoffee.internshipapp.Beans.Routes;

import org.w3c.dom.Text;

import java.util.Map;

import static com.overcoffee.internshipapp.R.id.toolbar;

public class AddRouteActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar2;
    /////finding views
    EditText fromR;
    EditText toR;
    EditText titleR;
    EditText fareR;
    EditText distanceR;
    EditText descriptionR;
    EditText timeR;
    Button addbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        fromR = (EditText) findViewById(R.id.fromroute);
        toR = (EditText) findViewById(R.id.toroute);
        titleR = (EditText) findViewById(R.id.titleroute);
        fareR = (EditText) findViewById(R.id.fareroute);
        distanceR = (EditText) findViewById(R.id.distanceroute);
        descriptionR = (EditText) findViewById(R.id.descriptionroute);
        timeR = (EditText) findViewById(R.id.timeroute);

        addbutton = (Button) this.findViewById(R.id.searchbuttonroute);

        addbutton.setOnClickListener(this);
        setupActionBar();


    }


    public void setupActionBar() {

        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar2.setTitle("Add New Route");
        //TextView title = (TextView) toolbar2.findViewById(R.id.toolbar_text2);
        //title.setText("Add New Route");
        setSupportActionBar(toolbar2);
    }


    @Override
    public void onClick(View view) {

        ///Create new route
        if(fromR.getText().toString().isEmpty() ||
                toR.getText().toString().isEmpty() ||
                titleR.getText().toString().isEmpty() ||
                fareR.getText().toString().isEmpty()||
                distanceR.getText().toString().isEmpty()||
                descriptionR.getText().toString().isEmpty()||
                timeR.getText().toString().isEmpty()){
            Toast.makeText(this,"Please fill all the fields!!!",Toast.LENGTH_LONG).show();
            return;

        }

        Routes newRoute = new Routes();
        newRoute.from = String.valueOf(fromR.getText());
        newRoute.to = String.valueOf(toR.getText());
        newRoute.title = String.valueOf(titleR.getText());
        newRoute.fare = Double.parseDouble(String.valueOf(fareR.getText()));
        newRoute.distance = Double.parseDouble(String.valueOf(distanceR.getText()));
        newRoute.description = String.valueOf(descriptionR.getText());
        newRoute.time = Integer.parseInt(String.valueOf(timeR.getText()));

        //////Add to database
        Backendless.Persistence.of(Routes.class).save(newRoute, new AsyncCallback<Routes>() {
            @Override
            public void handleResponse(Routes response) {

                TextView label = new TextView(AddRouteActivity.this);
                label.setText("The New Route Have Been Added Successfully");
                setContentView(label);

                finish();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("MYAPP", "Server reported an error " + fault.getMessage());
            }
        });
    }
}
