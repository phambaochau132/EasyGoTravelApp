package com.example.easygo_travelapp.activity;

import static com.example.easygo_travelapp.adapter.ScenicAdapter.ID_SCENIC;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.adapter.PhotoAdapter;
import com.example.easygo_travelapp.adapter.ReviewAdapter;
import com.example.easygo_travelapp.adapter.ScenicAdapter;
import com.example.easygo_travelapp.adapter.TourAndTicketAdapter;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.DetailScenic;
import com.example.easygo_travelapp.model.Review;
import com.example.easygo_travelapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ItemDetailsActivity extends BaseActivity implements View.OnClickListener {
    private CTToolbar toolbar;
    private ImageView bg_header;
    private TextView location;
    private TextView name;
    private TextView tvNumReview;
    private TextView tvReview;
    private TextView btnReview;
    private TextView description;
    private EditText edtReview;
    private ImageView star1, star2, star3, star4, star5;
    private RecyclerView rvPhotos, rvTourAndTickets, rvReviews;
    private PhotoAdapter photoAdapter;
    private TourAndTicketAdapter tourAndTicketAdapter;
    private ReviewAdapter reviewAdapter;
    private ConstraintLayout layoutWriteReview;
    private ImageView avatarReview;
    private ImageButton btnSend;
    private int position;
    private DatabaseReference myRef;
    private int idReview;
    private String idUser;
    public static DetailScenic scenic;

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        bg_header = findViewById(R.id.bg_header);
        location = findViewById(R.id.tvLocation);
        name = findViewById(R.id.tvTitle);
        tvReview = findViewById(R.id.tvReviews);
        tvNumReview = findViewById(R.id.review);
        btnReview = findViewById(R.id.btnWriteReview);
        edtReview = findViewById(R.id.edtWriteReview);
        description = findViewById(R.id.tvDescription);
        star1 = findViewById(R.id.imgStar1);
        star2 = findViewById(R.id.imgStar2);
        star3 = findViewById(R.id.imgStar3);
        star4 = findViewById(R.id.imgStar4);
        star5 = findViewById(R.id.imgStar5);
        rvPhotos = findViewById(R.id.rvPhotos);
        rvTourAndTickets = findViewById(R.id.rvTourAndTicket);
        rvReviews = findViewById(R.id.rvReviews);
        layoutWriteReview = findViewById(R.id.layoutWriteReview);
        avatarReview = findViewById(R.id.imgAvatar);
        btnSend = findViewById(R.id.btnSend);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        init();
        initAction();
        position = getIntent().getIntExtra(ScenicAdapter.ID_SCENIC, -1);
        getAvatarUser();
        myRef = referenceScenic.child(String.valueOf(position));
        getDataScenic();
        toolbar.setVisibleBack();
        toolbar.setVisibleMenuDetail();
    }

    private void initAction() {
        btnReview.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        location.setOnClickListener(this);
    }

    private void displayAvatarReview(String idUser) {
        // Read from the database
        FirebaseDatabase.getInstance().getReference().child(BaseActivity.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Picasso.get().load(user.getUrlAvatar()).into(avatarReview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataScenic() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scenic = snapshot.getValue(DetailScenic.class);
                setDataItem(scenic);
                updateFavorite(firebaseDatabase.getReference(FAVOURITE + "/" + firebaseUser.getUid() + "/" + TOURS));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateFavorite(DatabaseReference myRef) {
        int idScenic = scenic.getIdScenic();
        boolean flag = false;
        for (Integer favourite : favourites) {
            if (favourite == idScenic) {
                flag = true;
                break;
            }
        }

        if (flag == false) {
            Map<String, Integer> map = new HashMap<>();
            map.put("idTour", idScenic);
            myRef.child(String.valueOf(favourites.size())).setValue(map);
        }
    }

    public void setDataItem(DetailScenic scenic) {
        System.out.println("Chau" + scenic.getIdScenic());
        if (!scenic.getImageScenic().isEmpty()) {
            Picasso.get().load(scenic.getImageScenic()).into(bg_header);
        }

        location.setText(scenic.getLocation());
        name.setText(scenic.getNameScenic());
        description.setText(scenic.getDescription());
        setRatingStar(this, scenic.getRating(), star1, star2, star3, star4, star5);
        int nReview = scenic.getReview() != null ? scenic.getReview().size() : 0;
        tvNumReview.setText(nReview + " " + getString(R.string.review));
        tvReview.setText(getString(R.string.review) + " (" + nReview + ")");

        String[] sPhotos = scenic.getPhotos().split(",");
        if (scenic.getPhotos() != null) {
            rvPhotos.setLayoutManager(new GridLayoutManager(ItemDetailsActivity.this, 3));
            photoAdapter = new PhotoAdapter(ItemDetailsActivity.this, sPhotos);
            rvPhotos.setAdapter(photoAdapter);
        }

        if (scenic.getCities() != null) {
            rvTourAndTickets.setLayoutManager(new LinearLayoutManager(ItemDetailsActivity.this));
            tourAndTicketAdapter = new TourAndTicketAdapter(ItemDetailsActivity.this, scenic.getCities());
            rvTourAndTickets.setAdapter(tourAndTicketAdapter);
        }

        if (scenic.getReview() != null) {
            rvReviews.setLayoutManager(new LinearLayoutManager(ItemDetailsActivity.this));
            reviewAdapter = new ReviewAdapter(ItemDetailsActivity.this, scenic.getReview());
            rvReviews.setAdapter(reviewAdapter);
        }
    }

    private void getAvatarUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idUser = user.getUid();
            displayAvatarReview(idUser);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnReview.getId()) {
            layoutWriteReview.setVisibility(View.VISIBLE);
            edtReview.requestFocus();
        }
        if (v.getId() == btnSend.getId()) {
            if (!edtReview.getText().toString().trim().isEmpty()) {
                idReview = rvReviews.getChildCount() == 0 ? 0 : reviewAdapter.getItemCount();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String sCurrentTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                Review review = new Review(idUser, sCurrentTime, edtReview.getText().toString());
                myRef.child(String.valueOf(REVIEW)).child(String.valueOf(idReview)).setValue(review);
                edtReview.setText(null);
                edtReview.clearFocus();
            }
        }
        if (v.getId() == location.getId()) {
            if (location.getText() != null) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, TourDetailActivity.class);
                bundle.putSerializable(GET_TOUR, scenic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}