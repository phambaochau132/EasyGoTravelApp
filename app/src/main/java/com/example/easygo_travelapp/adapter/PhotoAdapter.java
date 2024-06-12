package com.example.easygo_travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easygo_travelapp.R;
import com.squareup.picasso.Picasso;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ItemViewHolder> {
    private Context context;
    private String[] photos;

    public PhotoAdapter(Context context, String[] photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String url = photos[position];
        System.out.println("Chau: " + photos.toString());
        try {
            Picasso.get().load(url).into(holder.imagePhotoScenic);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public int getItemCount() {
        return photos.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagePhotoScenic;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhotoScenic = itemView.findViewById(R.id.imgPhotoScenic);
        }
    }
}
