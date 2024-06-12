package com.example.easygo_travelapp.customView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.activity.ItemDetailsActivity;
import com.example.easygo_travelapp.adapter.ScenicAdapter;
import com.example.easygo_travelapp.model.ItemScenic;

import java.util.List;

public class CTRecyclerView extends ConstraintLayout implements View.OnClickListener {
    private TextView tvTitle, tvViewAll;
    private RecyclerView rvListItem;

    public CTRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUIComponent();
    }

    private void initUIComponent() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.ctv_list_item, this, true);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvViewAll = view.findViewById(R.id.tvViewAll);
        rvListItem = findViewById(R.id.rvListItem);
    }

    private void initActionComponent() {
        this.tvViewAll.setOnClickListener(this);
    }

    public void showDataRecycleView(List<ItemScenic> list, RecyclerView.LayoutManager layoutManager) {
        ScenicAdapter adapter = new ScenicAdapter(getContext(), list);
        this.rvListItem.setLayoutManager(layoutManager);
        this.rvListItem.setAdapter(adapter);
    }

    public void setTitleRecycleView(String title) {
        this.tvTitle.setText(title);
    }

    public TextView getTvViewAll() {
        return tvViewAll;
    }

    @Override
    public void onClick(View view) {

    }
}
