package com.example.easygo_travelapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easygo_travelapp.R;

public class OnboardFragment extends Fragment {

    private static final String KEY_PAGE = "number_page";

    public static OnboardFragment newInstance(int page) {
        OnboardFragment fragment = new OnboardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        switch (getArguments() != null ? getArguments().getInt(KEY_PAGE) : 0) {
            case 1:
                rootView = inflater.inflate(R.layout.fragment_onboarding1, container, false);
                break;
            case 2:
                rootView = inflater.inflate(R.layout.fragment_onboarding2, container, false);
                break;
            case 3:
                rootView = inflater.inflate(R.layout.fragment_onboarding3, container, false);

                break;
        }
        return rootView;
    }
}