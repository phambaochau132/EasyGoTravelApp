package com.example.easygo_travelapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.easygo_travelapp.fragment.OnboardFragment;

public class StatePagerAdapter extends FragmentPagerAdapter {
    public StatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return OnboardFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
