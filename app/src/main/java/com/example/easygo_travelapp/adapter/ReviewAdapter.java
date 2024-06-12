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
import com.example.easygo_travelapp.model.Review;
import com.example.easygo_travelapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {
    private Context context;
    private List<Review> listReview;

    public ReviewAdapter(Context context,List<Review>listReview) {
        this.context = context;
        this.listReview = listReview;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//        Review review=listReview.get(listReview.size()-(position+1));
        Review review = listReview.get(position);
        getInforUserFromFB(holder, review.getIdUser());
        holder.content.setText(review.getContent());

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sCurrentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
            Date currentTime = simpleDateFormat.parse(sCurrentTime);
            Date postTime = simpleDateFormat.parse(review.getTime());
            long time = currentTime.getTime() - postTime.getTime();
            int hours = (int) (time / (1000 * 60 * 60));
            int mins = (int) (time / (1000 * 60)) % 60;
            int days = (int) (time / (24 * 60 * 60 * 1000));

            if (days > 0) {
                holder.time.setText(days + " ngày trước");
            } else {
                if (hours > 0) {
                    holder.time.setText(hours + " h trước");
                } else {
                    holder.time.setText(mins + " ' trước");
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getInforUserFromFB(ItemViewHolder holder, String idUser){
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference(BaseActivity.USERS+"/"+idUser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user.getUrlAvatar() != null) {
                    Picasso.get().load(user.getUrlAvatar()).into(holder.avatar);
                }
                if (user.getUserName() != null) {
                    holder.fullName.setText(user.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView fullName, time, content;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.imgAvatar);
            fullName=itemView.findViewById(R.id.tvFullName);
            time=itemView.findViewById(R.id.tvTime);
            content=itemView.findViewById(R.id.tvContent);
        }
    }
}
