package com.example.easygo_travelapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTRecyclerView;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.example.easygo_travelapp.model.ItemScenic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private CTToolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private CTRecyclerView ctRecyclerViewTour, ctRecyclerViewDeal;
    List<ItemScenic> listScenic = new ArrayList<>();
    List<DetailScenic> listTour = new ArrayList<>();

    private void init() {
        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.toolbar = findViewById(R.id.toolbar);
        this.navigationView = findViewById(R.id.navigationView);
        this.bottomNavView = findViewById(R.id.bottom_navigation);
        this.ctRecyclerViewTour = findViewById(R.id.ctRVLove);
        this.ctRecyclerViewDeal = findViewById(R.id.ctRVDeal);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initActionToolbar(toolbar, drawerLayout, navigationView);
        initActionSearch(toolbar);
        initActionBottomNavView(bottomNavView);
        ctRecyclerViewTour.setTitleRecycleView(getString(R.string.destinations_we_love));
        ctRecyclerViewDeal.setTitleRecycleView(getString(R.string.deals));
        getDataScenic(referenceScenic);
        eventShowAllTours(this);
    }

    private void eventShowAllTours(Context context) {
        ctRecyclerViewTour.getTvViewAll().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Chau item");
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, TourActivity.class);
                bundle.putSerializable(GET_TOUR, (Serializable) listTour);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getDataScenic(DatabaseReference myRef) {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listScenic.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ItemScenic itemScenic = itemSnapshot.getValue(ItemScenic.class);
                    DetailScenic itemTourAndTicket = itemSnapshot.getValue(DetailScenic.class);
                    listScenic.add(itemScenic);
                    listTour.add(itemTourAndTicket);
                    System.out.println("Chau: " + listScenic.toString());
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                ctRecyclerViewTour.showDataRecycleView(listScenic, linearLayoutManager);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                ctRecyclerViewDeal.showDataRecycleView(listScenic, gridLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}