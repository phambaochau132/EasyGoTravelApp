package com.example.easygo_travelapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.easygo_travelapp.R;
import com.example.easygo_travelapp.customView.CTEditText;
import com.example.easygo_travelapp.customView.CTToolbar;
import com.example.easygo_travelapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
    private CTToolbar toolbar;
    private ImageView avatar;
    private ImageButton btnCamera;
    private CTEditText ctUsername, ctEmail, ctPhone, ctGender, ctBirthday;
    private TextView tvSave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private User user;
    private Uri uri;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                uri = result.getData().getData();
                Bitmap bitmap = null;
                try {
                    bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                avatar.setImageBitmap(bitmap);
                updateFromDataInMemory();
            }
        }
    });

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        tvSave = findViewById(R.id.tvSave);
        avatar = findViewById(R.id.imgAvatar);
        btnCamera = findViewById(R.id.btnCamera);
        ctUsername = findViewById(R.id.ctEdtUserName);
        ctEmail = findViewById(R.id.ctEdtEmail);
        ctPhone = findViewById(R.id.ctEdtPhone);
        ctGender = findViewById(R.id.ctEdtGender);
        ctBirthday = findViewById(R.id.ctEdtBirthday);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        setToolbar(toolbar);
        user = (User) getIntent().getBundleExtra(GET_USER).getSerializable(GET_USER);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference(USERS);
        setInforUser(user.getUrlAvatar(), user.getUserName(), user.getEmail(), user.getPhone(), user.getGender(), user.getBirthday());
        initAction();
    }

    private void initAction() {
        tvSave.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
    }

    private void updateFromDataInMemory() {
        // Get the data from an ImageView as bytes
        avatar.setDrawingCacheEnabled(true);
        avatar.buildDrawingCache();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("avatars/" + getNameImage(uri));
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Loi " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url="https://"+taskSnapshot.getUploadSessionUri().getHost()+
                        taskSnapshot.getUploadSessionUri().getPath()+"/avatars%2F"+
                        taskSnapshot.getStorage().getName()+"?alt=media";

                user.setUrlAvatar(url);
            }
        });
    }

    private String getNameImage(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int cursorIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(cursorIndex);
        String[] name = path.split("/");
        return name[name.length - 1];
    }

    private void setInforUser(String urlAvatar, String userName, String email, String phone, String gender, String birthday) {
        ctUsername.setTitle(getString(R.string.username));
        ctEmail.setTitle(getString(R.string.email));
        ctPhone.setTitle(getString(R.string.phone));
        ctGender.setTitle(getString(R.string.gender));
        ctBirthday.setTitle(getString(R.string.date_of_birth));

        urlAvatar = urlAvatar == null ? "https://www.i-music.com.hk/assets/images/no-avatar.png" : urlAvatar;
        userName = userName == null ? getString(R.string.not_been_set) : userName;
        email = email == null ? getString(R.string.not_been_set) : email;
        phone = phone == null ? getString(R.string.not_been_set) : phone;
        gender = gender == null ? getString(R.string.not_been_set) : gender;
        birthday = birthday == null ? getString(R.string.not_been_set) : birthday;

        ctUsername.setContent(userName);
        ctEmail.setContent(email);
        ctPhone.setContent(phone);
        ctGender.setContent(gender);
        ctBirthday.setContent(birthday);
        Picasso.get().load(urlAvatar).into(avatar);
    }

    private void setToolbar(CTToolbar toolbar) {
        toolbar.setVisibleBack();
        toolbar.setTitleToolbar("edit profile");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == tvSave.getId()) {
            updateProfileUserFirebase();
            onBackPressed();
        }
        if (view.getId() == btnCamera.getId()) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.putExtra("data_result", RESULT_OK);
            mActivityResultLauncher.launch(intent);
        }
    }

    private void updateProfileUserFirebase() {
        String sUserName = user.getUserName();
        String sUrlAvatar = user.getUrlAvatar();
        String sEmail = user.getEmail();
        String sPhone = user.getPhone();
        String sGender = user.getGender();
        String sBirthday = user.getBirthday();
        if (getCurrentFocus() != null) {
            getCurrentFocus().clearFocus();
        }

        if (!ctUsername.getContent().equals(getString(R.string.not_been_set))
                && !ctUsername.getContent().equals(user.getUserName())) {
            sUserName = ctUsername.getContent();
        }
        if (!ctEmail.getContent().equals(getString(R.string.not_been_set))
                && !ctEmail.getContent().equals(user.getEmail())) {
            sEmail = ctEmail.getContent();
        }
        if (!ctPhone.getContent().equals(getString(R.string.not_been_set))
                && !ctPhone.getContent().equals(user.getPhone())) {
            sPhone = ctPhone.getContent();
        }
        if (!ctGender.getContent().equals(getString(R.string.not_been_set))
                && !ctGender.getContent().equals(user.getGender())) {
            sGender = ctGender.getContent();
        }
        if (!ctBirthday.getContent().equals(getString(R.string.not_been_set))
                && !ctBirthday.getContent().equals(user.getBirthday())) {
            sBirthday = ctBirthday.getContent();
        }
        User newUser = new User(user.getIdUser(), sUrlAvatar, sUserName, sEmail, sPhone, sGender, sBirthday);
        myRef.child(user.getIdUser()).setValue(newUser);

    }
}