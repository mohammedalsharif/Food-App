package com.examples.foodapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.foodapp.databinding.ActivityDetailesBinding;
import com.examples.foodapp.model.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private static final String IS_FAVORITE_REF = "isFavorite";
    ActivityDetailesBinding binding;
    private DatabaseReference dbRef;
    private String currentUserId;
    private Intent intent;
    private String reference;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        binding.icBackDet.setOnClickListener(view -> {
            onBackPressed();
        });
        intent = getIntent();
        Log.e("TAG", "onCreate: " + reference);
        reference=intent.getStringExtra("Reference");
        food = (Food) intent.getSerializableExtra("Food");
        dbRef = FirebaseDatabase.getInstance().getReference(reference);
        if (food != null) {
            Picasso.get().load(food.getImageUrl()).centerCrop().fit().into(binding.foodImage, new Callback() {
                @Override
                public void onSuccess() {
                    binding.spinKit.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    binding.spinKit.setVisibility(View.VISIBLE);
                }
            });
            binding.etFoodNameDet.setText(food.getFoodName());
            binding.etFoodPriceDet.setText(food.getFoodPrice());
             checkedFavState();
        }


        binding.tpv.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
                if (like) {
                    setValueFavoriteFood("IsFavorite");
                } else {

                    setValueFavoriteFood("");
                }


            }
        });


    }

    private void checkedFavState() {
        dbRef.child(food.getId()).child(IS_FAVORITE_REF).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String fav = (String) dataSnapshot.getValue();
                if (fav.equals("IsFavorite")) {
                    binding.tpv.Like();
                }
            }
        });

    }

    private void setValueFavoriteFood(String isFavorite) {
        dbRef.child(food.getId()).child(IS_FAVORITE_REF).setValue(isFavorite).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
}