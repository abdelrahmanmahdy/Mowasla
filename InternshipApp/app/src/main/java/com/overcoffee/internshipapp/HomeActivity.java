package com.overcoffee.internshipapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {
    TextView nameTV, emailTV;
    RecyclerView navMenuRecyclerView;
    Toolbar toolbar;
    TextView title;
    ImageView profilePicture;
    DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //get data from Intent
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        //Finding Views
        nameTV = (TextView) findViewById(R.id.name);
        emailTV = (TextView) findViewById(R.id.email);
        profilePicture = (ImageView) findViewById(R.id.profile_picture);


        byte[] bytes=getIntent().getByteArrayExtra("pp");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        profilePicture.setImageBitmap(bmp);

        navMenuRecyclerView = (RecyclerView) findViewById(R.id.nav_menu_recycler);
        //Setting data to navigation menu
        emailTV.setText(email);
        nameTV.setText(name);
        //setting Nav Menu Recycler
        String[] texts = new String[]{"Text1", "Text2", "Text3", "Text4", "Text5"};
        NavMenuRecycler recycler = new NavMenuRecycler(texts, this);
        navMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        navMenuRecyclerView.setAdapter(recycler);
        //activate custom action bar
        setupActionBar();
        setupDrawer();
    }

    public void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) toolbar.findViewById(R.id.toolbar_text);
        title.setText("Home");
        setSupportActionBar(toolbar);
    }

    private class NavMenuRecycler extends RecyclerView.Adapter<NavMenuVH> {
        String[] recyclerItems;
        LayoutInflater inflater;

        NavMenuRecycler(String[] items, Context context) {
            recyclerItems = Arrays.copyOf(items, items.length);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public NavMenuVH onCreateViewHolder(ViewGroup parent, int viewType) {
            NavMenuVH view = new NavMenuVH(inflater.inflate(R.layout.side_menu_item, parent, false));
            return view;
        }

        @Override
        public void onBindViewHolder(NavMenuVH holder, int position) {
            holder.textView.setText(recyclerItems[position]);
        }

        @Override
        public int getItemCount() {
            return recyclerItems.length;
        }
    }

    private class NavMenuVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        NavMenuVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            title.setText(textView.getText());
        }
    }
}