package com.example.easygo_travelapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.activity.ItemDetailsActivity;
import com.example.easygo_travelapp.model.ItemScenic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScenicAdapter extends RecyclerView.Adapter<ScenicAdapter.ItemViewHolder> {
    public static final String ID_SCENIC = "id_scenic";
    private Context context;
    private List<ItemScenic> listScenic;
    private ItemScenic itemScenic;

    public ScenicAdapter(Context context, List<ItemScenic> listScenic) {
        this.context = context;
        this.listScenic = listScenic;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_scenic, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        itemScenic = listScenic.get(position);
        try {
            Picasso.get().load(itemScenic.getImageScenic()).into(holder.image);
            holder.name.setText(String.valueOf(itemScenic.getNameScenic()));
            holder.location.setText(itemScenic.getLocation());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, ItemDetailsActivity.class);
                    intent.putExtra(ID_SCENIC, holder.getAdapterPosition());
                    activity.startActivity(intent);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
        return listScenic.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name, location;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imgScenic);
            this.name = itemView.findViewById(R.id.tvScenicName);
            this.location = itemView.findViewById(R.id.tvLocation);
        }
    }
}
