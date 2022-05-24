package com.examples.foodapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.foodapp.databinding.ActivityDetailesBinding;
import com.examples.foodapp.model.Food;
import com.ldoublem.thumbUplib.ThumbUpView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Food food = (Food) getIntent().getSerializableExtra("Food");
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

        }


        binding.icBackDet.setOnClickListener(view -> {
            onBackPressed();
        });


        binding.tpv.setOnThumbUp(new ThumbUpView.OnThumbUp() {
            @Override
            public void like(boolean like) {
            }
        });
        binding.tpv.Like();
        binding.tpv.UnLike();
    }
}