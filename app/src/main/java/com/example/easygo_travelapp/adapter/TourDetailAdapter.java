package com.example.easygo_travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.model.City;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourDetailAdapter extends RecyclerView.Adapter<TourDetailAdapter.ItemViewHolder> {
    private Context context;
    private List<City> tourDetails;

    public TourDetailAdapter(Context context, List<City> tourDetails) {
        this.context = context;
        this.tourDetails = tourDetails;
    }

    @NonNull
    @Override
    public TourDetailAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tour_detail,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        City city = tourDetails.get(position);
        try {
            Picasso.get().load(city.getImageCity()).into(holder.imageScenic);
            System.out.println("Chau" + city.getNameCity());
            holder.nameScenic.setText(city.getNameCity());
            holder.rating.setText(String.valueOf(city.getRating()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
        return tourDetails.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageScenic;
        private final TextView nameScenic, rating, distance;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageScenic = itemView.findViewById(R.id.imgCity);
            this.nameScenic = itemView.findViewById(R.id.tvNameCity);
            this.rating = itemView.findViewById(R.id.tvRating);
            this.distance = itemView.findViewById(R.id.tvDistance);
        }
    }
}
