package com.example.easygo_travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.model.City;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAndTicketAdapter extends RecyclerView.Adapter<TourAndTicketAdapter.ItemViewHolder> {
    private Context context;
    private List<City> list;

    public TourAndTicketAdapter(Context context, List<City> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour_ticket, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        City tourAndTicket = list.get(position);
        try {
            Picasso.get().load(tourAndTicket.getImageCity()).into(holder.imgTour);
            holder.nameTour.setText(tourAndTicket.getNameCity());
            holder.price.setText("$" + tourAndTicket.getPrice());
            setRatingStar(tourAndTicket.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5);
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
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTour;
        private TextView nameTour, price;
        private ImageView star1, star2, star3, star4, star5;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgTour = itemView.findViewById(R.id.imgTour);
            this.nameTour = itemView.findViewById(R.id.tvNameTour);
            this.price = itemView.findViewById(R.id.tvPrice);
            this.star1 = itemView.findViewById(R.id.imgStar1);
            this.star2 = itemView.findViewById(R.id.imgStar2);
            this.star3 = itemView.findViewById(R.id.imgStar3);
            this.star4 = itemView.findViewById(R.id.imgStar4);
            this.star5 = itemView.findViewById(R.id.imgStar5);
        }
    }
}
