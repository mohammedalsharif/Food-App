package com.examples.foodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.databinding.ItemCartBinding;
import com.examples.foodapp.model.Food;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHoldar> {
    private List<Food> listCart;

    public void setList(List<Food> list) {
        this.listCart = list;
    }

    @NonNull
    @Override

    public CartViewHoldar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHoldar(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHoldar holder, int position) {
        Food food =listCart.get(position);
        Picasso.get().load(food.getImageUrl()).fit().into(holder.binding.foodImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.binding.spinKit.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                holder.binding.spinKit.setVisibility(View.VISIBLE);
            }
        });
        holder.binding.foodName.setText(food.getFoodName());
        holder.binding.foodPrice.setText(food.getFoodPrice());

        holder.binding.plusCart.setOnClickListener(view -> {

            int numberCart = Integer.parseInt(holder.binding.numberCart.getText().toString());
            holder.binding.numberCart.setText(String.valueOf(numberCart + 1));
            holder.binding.foodPrice.setText(increasePrice(holder.binding.foodPrice,numberCart));
        });

        holder.binding.removeCart.setOnClickListener(view -> {
            int numberCart = Integer.parseInt(holder.binding.numberCart.getText().toString());

            if (numberCart >= 2) {
                holder.binding.numberCart.setText(String.valueOf(numberCart - 1));
                holder.binding.foodPrice.setText(decreasePrice(holder.binding.foodPrice, numberCart));
            }

        });


    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class CartViewHoldar extends RecyclerView.ViewHolder {
        ItemCartBinding binding;

        public CartViewHoldar(@NonNull ItemCartBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    private String decreasePrice(TextView foodPrice, int numberCart) {
        String priseCart = foodPrice.getText().toString();
        String priseSub = priseCart.substring(0, priseCart.length() - 1);
        int price = Integer.parseInt(priseSub);
        return price - (price / numberCart) + "$";
    }

    private String increasePrice(TextView foodPrice, int numberCart) {
        String priseCart = foodPrice.getText().toString();
        String priseSub = priseCart.substring(0, priseCart.length() - 1);
        int price = Integer.parseInt(priseSub);
        return price + (price / numberCart) + "$";
    }
}
