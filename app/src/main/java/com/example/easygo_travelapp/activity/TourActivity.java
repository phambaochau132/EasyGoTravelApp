package com.example.easygo_travelapp.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.adapter.TourAdapter;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import java.util.List;

public class TourActivity extends BaseActivity {

    private CTToolbar toolbar;
    private RecyclerView rvTours;
    private List<DetailScenic> allTour;
    private List<Integer> favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        toolbar = findViewById(R.id.toolbar);
        rvTours = findViewById(R.id.rvTours);
        toolbar.setVisibleBack();
        toolbar.setTitleToolbar("Tours");
        Bundle bundle = getIntent().getExtras();
        allTour = BaseActivity.allTour;
        favourites = BaseActivity.favourites;
        TourAdapter adapter = new TourAdapter(TourActivity.this, this.allTour, this.favourites);
        rvTours.setLayoutManager(new LinearLayoutManager(TourActivity.this));
        rvTours.setAdapter(adapter);
    }
}