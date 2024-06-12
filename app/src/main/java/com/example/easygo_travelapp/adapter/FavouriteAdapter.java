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
import com.example.easygo_travelapp.activity.BaseActivity;
import com.example.easygo_travelapp.model.DetailScenic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ItemViewHolder> {
    private Context context;
    private List<DetailScenic> listFavourite;

    public FavouriteAdapter(Context context, List<DetailScenic> listFavourite) {
        this.context = context;
        this.listFavourite = listFavourite;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour_favourite, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ItemViewHolder holder, int position) {
        DetailScenic tour = listFavourite.get(position);
        Picasso.get().load(tour.getImageScenic()).into(holder.imgTour);
        holder.tvNameTour.setText(tour.getNameScenic());
        holder.location.setText(tour.getLocation());
        holder.timeTour.setText(tour.getTimeTour() + context.getString(R.string.days));
        BaseActivity.setRatingStar(context, tour.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5);
    }

    @Override
    public int getItemCount() {
        return listFavourite.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTour;
        private TextView tvNameTour, location, timeTour;
        private ImageView star1, star2, star3, star4, star5;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTour = itemView.findViewById(R.id.imgTour);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            location = itemView.findViewById(R.id.tvLocation);
            timeTour = itemView.findViewById(R.id.tvTimeTour);
            star1 = itemView.findViewById(R.id.imgStar1);
            star2 = itemView.findViewById(R.id.imgStar2);
            star3 = itemView.findViewById(R.id.imgStar3);
            star4 = itemView.findViewById(R.id.imgStar4);
            star5 = itemView.findViewById(R.id.imgStar5);
        }
    }
}
