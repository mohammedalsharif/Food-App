package com.examples.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.databinding.ItemFoodBinding;
import com.examples.foodapp.model.Food;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.viewHolder> {

    Context context;
    List<Food> listFood;
    setOnClickItemListener itemListener;


    public AdapterFood(Context context,setOnClickItemListener itemListener) {
        this.itemListener = itemListener;
        this.context=context;
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(context), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Food foods = listFood.get(position);
        Picasso.get().load(foods.getImageUrl()).fit().into(holder.binding.foodImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.binding.spinKit.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {
                holder.binding.spinKit.setVisibility(View.VISIBLE);
            }
        });
        holder.binding.foodName.setText(foods.getFoodName());
        holder.binding.foodPrice.setText(foods.getFoodPrice());
        holder.itemView.setOnClickListener(view -> {
            itemListener.OnClickItemListener(foods,
                    holder.binding.cardImage
                    , holder.binding.foodName,
                    holder.binding.foodPrice);

        });
    }

    @Override
    public int getItemCount() {
        return listFood != null ? listFood.size() : 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemFoodBinding binding;

        public viewHolder(@NonNull ItemFoodBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
