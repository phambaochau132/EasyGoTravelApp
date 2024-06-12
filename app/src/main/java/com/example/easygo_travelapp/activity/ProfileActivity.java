package com.example.easygo_travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.example.easygo_travelapp.model.User;
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
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private CTToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private ImageView avatar;
    private TextView fullName, tvVipMember, tvLogin;
    public static User currentUser;
    public static DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


    private void init() {
        myRef = firebaseDatabase.getReference(USERS + "/" + firebaseUser.getUid());
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        bottomNavView = findViewById(R.id.bottom_navigation);
        avatar = findViewById(R.id.imgAvatar);
        fullName = findViewById(R.id.tvFullName);
        tvVipMember = findViewById(R.id.tvVipMember);
//        tvLogin=findViewById(R.id.tvLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        initActionToolbar(toolbar, drawerLayout, navigationView);
        initActionMenuDetail(toolbar);
        initActionBottomNavView(bottomNavView);
        updateBottomNavState(bottomNavView);
        getCurrentUser();
        initAction();
    }

    private void initAction() {
        fullName.setOnClickListener(this);
        avatar.setOnClickListener(this);
    }

    private void getCurrentUser() {
        if (firebaseUser != null) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    currentUser = user;
                    if (user == null) {
                        String path = firebaseUser.getPhotoUrl() == null ? null : firebaseUser.getPhotoUrl().getPath();
                        createProfileUserFirebase(firebaseUser.getUid(), path, firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhoneNumber(), null, null);
                    } else {
                        setDataForUi(user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

//        if (user!=null){
//            fullName.setVisibility(View.VISIBLE);
//            tvVipMember.setVisibility(View.VISIBLE);
//            tvLogin.setVisibility(View.GONE);
//
//            getUserProfile(user.getUid());
//        }
//        else {
//            fullName.setVisibility(View.GONE);
//            tvVipMember.setVisibility(View.GONE);
//            tvLogin.setVisibility(View.VISIBLE);
//            Picasso.get().load("https://www.i-music.com.hk/assets/images/no-avatar.png").into(avatar);
//        }
    }

//    private void getUserProfile(FirebaseUser firebaseUser, DatabaseReference myRef) {
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int count = 0;
//                int total = (int) dataSnapshot.getChildrenCount();
//
//                if (total == 0) {
//
//                } else {
//                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//                        User user = itemSnapshot.getValue(User.class);
//                        count++;
//                        if (user.getIdUser().equals(firebaseUser.getUid())) {
//                            setDataForUi(user);
//                            break;
//                        }
//                        if (count == total) {
//                            String path = firebaseUser.getPhotoUrl() == null ? null : firebaseUser.getPhotoUrl().getPath();
//                            createProfileUserFirebase(firebaseUser.getUid(), firebaseUser.g, path, firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhoneNumber(), null, null);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });
//    }

    private void createProfileUserFirebase(String idUser, String urlAvatar, String userName, String email, String phone, String gender, String birthday) {
        User newUser = new User(idUser, urlAvatar, userName, email, phone, gender, birthday);
        myRef.setValue(newUser);
    }

    private void setDataForUi(User user) {
        if (user.getUrlAvatar() != null) {
            Picasso.get().load(user.getUrlAvatar()).into(avatar);
        } else {
            Picasso.get().load("https://www.i-music.com.hk/assets/images/no-avatar.png").into(avatar);
        }
        if (user.getUserName() != null) {
            fullName.setText(user.getUserName());
        } else {
            fullName.setText(getString(R.string.not_been_set));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GET_USER, currentUser);
        intent.putExtra(GET_USER, bundle);
        startActivity(intent);
    }
}