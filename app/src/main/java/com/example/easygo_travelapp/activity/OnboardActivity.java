package com.example.easygo_travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.adapter.StatePagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OnboardActivity extends BaseActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    private StatePagerAdapter adapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout llMenu;
    private TextView tvSkip, tvNext, btnGetStarted;

    private void init() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabPager);
        llMenu = findViewById(R.id.menu);
        tvSkip = findViewById(R.id.tvSkip);
        tvNext = findViewById(R.id.tvNext);
        btnGetStarted = findViewById(R.id.getStarted);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);
        init();
        adapter = new StatePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcon();
        initAction();
    }

    private void setTabIcon() {
        tabLayout.setTabIconTint(getColorStateList(R.color.color_D8D8D8));
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(R.drawable.ic_action_tab);
            if (i == 0)
                tabLayout.getTabAt(0).getIcon().setTint(getColor(R.color.color_0055D4));
        }
    }

    private void initAction() {
        tabLayout.addOnTabSelectedListener(this);
        tvSkip.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        btnGetStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == tvSkip.getId()) {
            Intent intent = new Intent(OnboardActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }

        if (view.getId() == tvNext.getId()) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }

        if (view.getId() == btnGetStarted.getId()) {
            Intent intent = new Intent(OnboardActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == tabLayout.getTabCount() - 1) {
            llMenu.setVisibility(View.GONE);
            btnGetStarted.setVisibility(View.VISIBLE);
        }
        tab.getIcon().setTint(getColor(R.color.color_0055D4));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.getIcon().setTint(getColor(R.color.color_D8D8D8));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}