package com.examples.foodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.databinding.ItemResFavfoodBinding;

import java.util.List;

public class AdapterFavFood extends RecyclerView.Adapter<AdapterFavFood.FavViewHolred> {
    List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FavViewHolred onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResFavfoodBinding binding =ItemResFavfoodBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FavViewHolred(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolred holder, int position) {
        if (position==1){
            holder.binding.viewInRec.setVisibility(View.VISIBLE);
        }else {
            holder.binding.viewInRec.setVisibility(View.GONE);
        }
        holder.binding.foodName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list!=null ? list.size() : null;
    }

    public class FavViewHolred extends RecyclerView.ViewHolder {
        ItemResFavfoodBinding binding;
        public FavViewHolred(@NonNull ItemResFavfoodBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }
}
