package com.example.easygo_travelapp.customView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.activity.MainActivity;
import com.google.android.material.navigation.NavigationView;

public class CTToolbar extends ConstraintLayout implements View.OnClickListener{
    private ImageButton icMenu,icMenuDetail,icBack,icShare,icSearch;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Context context;
    private TextView title;

    public CTToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initComponent();
    }

    public void initComponent() {
        View view = LayoutInflater
                .from(getContext())
                .inflate(R.layout.ctv_toolbar, this, true);
        this.icMenu=view.findViewById(R.id.icMenu);
        this.icMenuDetail=view.findViewById(R.id.icMenuDetail);
        this.icBack=view.findViewById(R.id.icBack);
        this.icShare=view.findViewById(R.id.icShare);
        this.icSearch=view.findViewById(R.id.icSearch);
        this.title=findViewById(R.id.tvTitle);
    }
    public void setTitleToolbar(String title){
        this.title.setText(title);
    }

    public void setVisibleMenu(DrawerLayout drawerLayout,NavigationView navigationView) {
        this.icMenu.setVisibility(VISIBLE);
        this.icMenu.setOnClickListener(this);
        this.drawerLayout=drawerLayout;
        this.navigationView=navigationView;
    }

    public void setVisibleMenuDetail() {
        this.icMenuDetail.setVisibility(VISIBLE);
        this.icMenuDetail.setOnClickListener(this);
    }

    public void setVisibleBack() {
        this.icBack.setVisibility(VISIBLE);
        this.icBack.setOnClickListener(this);
    }

    public void setVisibleShare() {
        this.icShare.setVisibility(VISIBLE);
        this.icShare.setOnClickListener(this);
    }

    public void setVisibleSearch(){
        this.icSearch.setVisibility(VISIBLE);
        this.icSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==icMenu.getId())
        {
            this.drawerLayout.openDrawer(GravityCompat.START);
        }
        if (view.getId()==icMenuDetail.getId()){

        }
        if (view.getId()==icBack.getId()){
            Activity activity=(Activity)context;
            activity.onBackPressed();
        }
        if (view.getId()==icShare.getId()){

        }

        if (view.getId()==icSearch.getId()){

        }
    }
}
