package com.example.easygo_travelapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationBarView.OnItemSelectedListener {
    public static final String USERS = "users";
    public static final String SCENICS = "scenics";
    public static final String FAVOURITE = "favourites";
    public static final String TOURS = "tours";
    public static final String REVIEW = "review";
    public static final String GET_USER = "get_user";
    public static final String GET_TOUR = "get_tours";
    public static FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DrawerLayout drawerLayout;
    public static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference referenceFavorite = firebaseDatabase.getReference(FAVOURITE + "/" + firebaseUser.getUid() + "/" + TOURS);
    public static DatabaseReference referenceScenic = firebaseDatabase.getReference(SCENICS);
    public static List<DetailScenic> allTour = getFireBaseTours();
    public static List<Integer> favourites = getFireBaseFavourite();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("################### ACTIVITY ########################");
        System.out.println("Activity name: " + this.getLocalClassName());
        System.out.println("######################################################");
    }

    public static void setRatingStar(Context context, double rating, ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5) {
        while (rating != 0) {
            if (rating < 5) {
                star5.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_BEC2CE));
            } else break;
            if (rating < 4) {
                star4.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_BEC2CE));
            } else break;
            if (rating < 3) {
                star3.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_BEC2CE));
            } else break;
            if (rating < 2) {
                star2.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_BEC2CE));
            } else break;
            if (rating < 1) {
                star1.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.color_BEC2CE));
            } else break;
        }
    }

    public void initActionToolbar(CTToolbar toolbar, DrawerLayout drawerLayout, NavigationView navigationView) {
        toolbar.setVisibleMenu(drawerLayout, navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        this.drawerLayout = drawerLayout;
    }

    public void initActionBack(CTToolbar toolbar) {
        toolbar.setVisibleBack();
    }

    public void initActionMenuDetail(CTToolbar toolbar) {
        toolbar.setVisibleMenuDetail();
    }

    public void initActionShare(CTToolbar toolbar) {
        toolbar.setVisibleShare();
    }

    public void initActionSearch(CTToolbar toolbar) {
        toolbar.setVisibleSearch();
    }


    public void initActionBottomNavView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    public void updateBottomNavState(BottomNavigationView bottomNavigationView) {
        int itemId = getIntent().getIntExtra("NavItemId", -1);
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(itemId).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.btn_explore:

            case R.id.nav_home:

                intent = new Intent(this, MainActivity.class);
                break;

            case R.id.btn_profile:

            case R.id.nav_profile:
                intent = new Intent(this, ProfileActivity.class);
                break;

            case R.id.btn_favourite:

            case R.id.nav_favourite:
                intent = new Intent(this, FavouriteActivity.class);

                break;

            case R.id.btn_my_trip:
                break;

            case R.id.nav_my_trip:
                break;

            case R.id.nav_log_out:
                break;
        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("NavItemId", item.getItemId());

        startActivity(intent);
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public static List<DetailScenic> getFireBaseTours() {
        List<DetailScenic> result = new ArrayList<>();
        referenceScenic.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                result.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    DetailScenic item = itemSnapshot.getValue(DetailScenic.class);
                    result.add(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Chau Loi" + error);
            }
        });
        return result;
    }

    public static List<Integer> getFireBaseFavourite() {
        List<Integer> result = new ArrayList<>();
        referenceFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                result.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) itemSnapshot.getValue();

                    int id = Integer.parseInt(map.get("idTour").toString());
                    result.add(id);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Chau Loi" + error);
            }
        });
        return result;
    }
}
