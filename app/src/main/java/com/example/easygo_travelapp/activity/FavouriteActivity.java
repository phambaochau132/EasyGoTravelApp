package com.example.easygo_travelapp.activity;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.adapter.FavouriteAdapter;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends BaseActivity {
    private CTToolbar toolbar;
    public RecyclerView rvFavourite;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    public List<Integer> favourites;
    public List<DetailScenic> ListTour;
    private List<DetailScenic> toursFavourite = new ArrayList<>();

    private void init() {
        toolbar = findViewById(R.id.toolbarFavourite);
        rvFavourite = findViewById(R.id.rvFavourite);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        init();
        initActionToolbar(toolbar, drawerLayout, navigationView);
        initActionMenuDetail(toolbar);
        initActionBottomNavView(bottomNavigationView);
        updateBottomNavState(bottomNavigationView);
        ListTour = BaseActivity.allTour;
        favourites = BaseActivity.favourites;
        getToursFavourite(this.toursFavourite);
        FavouriteAdapter adapter = new FavouriteAdapter(this, this.toursFavourite);
        rvFavourite.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvFavourite.setAdapter(adapter);

    }

    public void getToursFavourite(List<DetailScenic> toursFavourite) {
        for (Integer idTour : favourites) {
            for (DetailScenic tour : allTour) {
                if (idTour == tour.getIdScenic()) {
                    toursFavourite.add(tour);
                    break;
                }
            }
        }
    }
}