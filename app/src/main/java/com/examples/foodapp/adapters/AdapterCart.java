package com.examples.foodapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.databinding.ItemCartBinding;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHoldar> {
    private List<String> listCart;

    public void setList(List<String> list) {
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
        holder.binding.foodName.setText(listCart.get(position));

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
        String priseSub = priseCart.substring(0, priseCart.length() - 2);
        int price = Integer.parseInt(priseSub);
        return price - (price / numberCart) + " $";
    }

    private String increasePrice(TextView foodPrice, int numberCart) {
        String priseCart = foodPrice.getText().toString();
        String priseSub = priseCart.substring(0, priseCart.length() - 2);
        int price = Integer.parseInt(priseSub);
        return price + (price / numberCart) + " $";
    }
}
