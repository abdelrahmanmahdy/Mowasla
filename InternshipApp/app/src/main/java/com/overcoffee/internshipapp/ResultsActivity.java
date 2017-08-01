package com.overcoffee.internshipapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesham on 11/07/2017.
 */

public class ResultsActivity extends AppCompatActivity
    {

    private RecyclerView recycler_view;
    private RecyclerView.Adapter recycler_view_adapter;
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //setting the action bar title
        //setTitle("Results");

        //recycler view creation
        recycler_view = (RecyclerView) findViewById(R.id.recycler1);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //creation of random paths
        list = new ArrayList<>();
        list.add(new ResultItem("maadi to october to mokkatam", "60 minuites", "5 LE"));
        list.add(new ResultItem("maadi to new cairo", "23 minuites", "15 LE"));
        list.add(new ResultItem("maadi to blablablablablabla", "52 minuites", "2 LE"));
        list.add(new ResultItem("maadi to blablabla", "30 minuites", "45 LE"));

        recycler_view_adapter = new ResultsAdapter(list, this);
        recycler_view.setAdapter(recycler_view_adapter);
        }
    }
