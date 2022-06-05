package com.examples.foodapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.foodapp.R;
import com.examples.foodapp.databinding.ActivityDetailesBinding;
import com.examples.foodapp.model.Food;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private DatabaseReference dbRefListCart;

    private Intent intent;
    private String reference;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.icBackDet.setOnClickListener(view -> {
            onBackPressed();
        });
        intent = getIntent();
        Log.e("TAG", "onCreate: " + reference);
        reference=intent.getStringExtra("Reference");
        food = (Food) intent.getSerializableExtra("Food");
        dbRef = FirebaseDatabase.getInstance().getReference();
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
                    Toast.makeText(DetailsActivity.this, "is Favorite", Toast.LENGTH_SHORT).show();
                } else {
                    setValueFavoriteFood("");
                    Toast.makeText(DetailsActivity.this, "Un Favorite", Toast.LENGTH_SHORT).show();
                }


            }
        });
        binding.addBtn.setOnClickListener(view -> {
            dbRef.child("listCart").child(food.getId()).setValue(food).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(DetailsActivity.this, R.string.add_to_cart, Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void checkedFavState() {
        dbRef.child(reference).child(food.getId()).child(IS_FAVORITE_REF).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
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
        dbRef.child(reference).child(food.getId()).child(IS_FAVORITE_REF).setValue(isFavorite).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }
}