package com.example.easygo_travelapp.adapter;

import static com.example.easygo_travelapp.adapter.ScenicAdapter.ID_SCENIC;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.activity.ItemDetailsActivity;
import com.example.easygo_travelapp.model.City;
import com.example.easygo_travelapp.model.DetailScenic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ItemVIewHolder> {
    private Context context;
    private List<DetailScenic> tours;
    private List<Integer> favourites;

    public TourAdapter(Context context, List<DetailScenic> tours, List<Integer> favourites) {
        this.context = context;
        this.tours = tours;
        this.favourites = favourites;
    }

    @NonNull
    @Override
    public TourAdapter.ItemVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new ItemVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAdapter.ItemVIewHolder holder, int position) {
        DetailScenic tour = tours.get(position);
        int numCity = tour.getCities().size();
        for (int i = 0; i < tour.getCities().size(); i++) {
            int count = 0;
            for (int j = i; j < tour.getCities().size(); j++) {
                if (tour.getCities().get(i).getIdLocation() == tour.getCities().get(j).getIdLocation()) {
                    count = count++;
                }
            }
            if (count > 1) {
                numCity++;
            }
        }
        for (Integer key : favourites) {
            if (key == tour.getIdScenic()) {
                holder.bookMark.setVisibility(View.INVISIBLE);
                break;
            }
        }

        try {
            Picasso.get().load(tour.getImageScenic()).into(holder.imgTour);
            holder.tvNameTour.setText(tour.getNameScenic());
            holder.location.setText(tour.getLocation());
            holder.timeTour.setText(tour.getTimeTour() + " " + context.getString(R.string.days) + " - " + numCity + " " + context.getString(R.string.cities));
            holder.price.setText("$" + tour.getPrice());
            setRatingStar(tour.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    public void setRatingStar(double rating, ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5) {
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

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public class ItemVIewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTour;
        private TextView tvNameTour, location, timeTour, price;
        private ImageView star1, star2, star3, star4, star5, bookMark;

        public ItemVIewHolder(@NonNull View itemView) {
            super(itemView);
            imgTour = itemView.findViewById(R.id.imgTour);
            tvNameTour = itemView.findViewById(R.id.tvNameTour);
            location = itemView.findViewById(R.id.tvLocation);
            timeTour = itemView.findViewById(R.id.tvTimeTour);
            price = itemView.findViewById(R.id.tvPrice);
            bookMark = itemView.findViewById(R.id.imgBookMark);
            star1 = itemView.findViewById(R.id.imgStar1);
            star2 = itemView.findViewById(R.id.imgStar2);
            star3 = itemView.findViewById(R.id.imgStar3);
            star4 = itemView.findViewById(R.id.imgStar4);
            star5 = itemView.findViewById(R.id.imgStar5);
        }
    }
}
