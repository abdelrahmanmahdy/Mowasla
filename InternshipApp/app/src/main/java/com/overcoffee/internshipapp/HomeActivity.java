package com.overcoffee.internshipapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.DataQueryBuilder;
import com.overcoffee.internshipapp.Beans.Routes;
import com.overcoffee.internshipapp.Beans.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView nameTV, emailTV;
    RecyclerView navMenuRecyclerView;
    Toolbar toolbar;
    TextView title, test1j;
    ImageView profilePicture;
    DrawerLayout mDrawerLayout;
    Spinner from_spinner, to_spinner;
    Button resultbutton;

    private RecyclerView recycler_view;
    private RecyclerView.Adapter recycler_view_adapter;
    private List list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //get data from Intent
        //String email = getIntent().getStringExtra("email");
        //Finding Views
        nameTV = (TextView) findViewById(R.id.name);
        emailTV = (TextView) findViewById(R.id.email);
        profilePicture = (ImageView) findViewById(R.id.profile_picture);
        from_spinner = (Spinner) findViewById(R.id.from_spinner);
        to_spinner = (Spinner) findViewById(R.id.to_spinner);
        resultbutton = (Button) findViewById(R.id.searchbutton);

        String currentUserId=Backendless.UserService.loggedInUser();
        Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                nameTV.setText(response.getProperty("name").toString());
                emailTV.setText(response.getEmail());
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        /*DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause("email = '" + email + "' ");
        Backendless.Data.of(Users.class).find(queryBuilder, new AsyncCallback<List<Users>>() {
            @Override
            public void handleResponse(List<Users> response) {

                nameTV.setText(response.get(0).name);
                Log.d("MOWASLA", " " + response.size());
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });
*/

        byte[] img = getIntent().getByteArrayExtra("pp");
        if (img != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            profilePicture.setImageBitmap(bmp);
        }
        navMenuRecyclerView = (RecyclerView) findViewById(R.id.nav_menu_recycler);
        //Setting data to navigation menu
        //emailTV.setText(email);

        //setting Nav Menu Recycler
        String[] texts = new String[]{"Add Route", "Settings", "Logout"};
        NavMenuRecycler recycler = new NavMenuRecycler(texts, this);
        navMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        navMenuRecyclerView.setAdapter(recycler);
        //activate custom action bar
        setupActionBar();
        setupDrawer();
        setupFromSpinner();
        resultbutton.setOnClickListener(this);
        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setWhereClause("from = '" + selected + "'");
                Backendless.Persistence.of(Routes.class).find(queryBuilder, new AsyncCallback<List<Routes>>() {
                    @Override
                    public void handleResponse(List<Routes> response) {
                        Set<String> Dupstrings = new HashSet<>();
                        for (Routes route : response) {
                            Dupstrings.add(route.to);

                        }
                        List<String> strings = new ArrayList<String>();
                        strings.addAll(Dupstrings);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this,
                                android.R.layout.simple_spinner_item, strings);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        to_spinner.setAdapter(adapter);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.d("MOWASLA", fault.getMessage());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        recycler_view = (RecyclerView) findViewById(R.id.recycler1);
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
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
        title.setText("Mowasla");
        setSupportActionBar(toolbar);
    }

    private void setupFromSpinner() {
        DataQueryBuilder builder = DataQueryBuilder.create();

        Backendless.Data.of(Routes.class).find(new AsyncCallback<List<Routes>>() {
            @Override
            public void handleResponse(List<Routes> response) {

                Set<String> locationsSet = new HashSet<>();
                for (int i = 0; i < response.size(); i++) {
                    locationsSet.add(response.get(i).from);
                }
                ////remove duplicates
                List<String> locations = new ArrayList<String>();
                locations.addAll(locationsSet);
                locationsSet.toString();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, locations);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                from_spinner.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        //to prevent crash if spinners are empty
        if (from_spinner.getSelectedItem() == null ||
                to_spinner.getSelectedItem() == null)
            return;

        String SelectedFrom = from_spinner.getSelectedItem().toString();
        String SelectedTo = to_spinner.getSelectedItem().toString();

        DataQueryBuilder builder = DataQueryBuilder.create();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause("from = '" + SelectedFrom + "' AND to='" + SelectedTo + "'");
        list = new ArrayList<>();
        Backendless.Data.of(Routes.class).find(queryBuilder, new AsyncCallback<List<Routes>>() {
            @Override
            public void handleResponse(List<Routes> response) {

                Set<String> locationsSet = new HashSet<>();
                for (int i = 0; i < response.size(); i++) {
                    locationsSet.add(response.get(i).title);
                    list.add(new ResultItem(response.get(i).title, String.valueOf(response.get(i).time) + " min", String.valueOf(response.get(i).fare) + " L.E", response.get(i).description));

                }

                recycler_view_adapter = new ResultsAdapter(list, HomeActivity.this);
                recycler_view.setAdapter(recycler_view_adapter);

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

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
            //title.setText(textView.getText());
            if (textView.getText() == "Add Route") {
                Intent intent = new Intent(HomeActivity.this, AddRouteActivity.class);
                startActivity(intent);
            }
            if (textView.getText() == "Logout") {
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(HomeActivity.this,"user logged out",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(HomeActivity.this,"sorry something went wrong!",Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}