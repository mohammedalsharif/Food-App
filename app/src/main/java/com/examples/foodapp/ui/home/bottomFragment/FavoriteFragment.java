package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.examples.foodapp.adapters.AdapterFavFood;
import com.examples.foodapp.databinding.FragmentFavoriteBinding;
import com.examples.foodapp.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {
    Query query;
    Query query2;
    Query query3;
    ValueEventListener valueEventListener;
    private DatabaseReference dbRef;


    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = FirebaseDatabase.getInstance().getReference("Foods").orderByChild("isFavorite").equalTo("IsFavorite");
        query2 = FirebaseDatabase.getInstance().getReference("Drinks").orderByChild("isFavorite").equalTo("IsFavorite");
        query3 = FirebaseDatabase.getInstance().getReference("Snacks").orderByChild("isFavorite").equalTo("IsFavorite");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFavoriteBinding binding = FragmentFavoriteBinding.inflate(inflater);
        AdapterFavFood adapterFood = new AdapterFavFood();
        ArrayList<Food> list = new ArrayList<>();
        adapterFood.setList(list);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Food food = snapshot1.getValue(Food.class);
                        list.add(food);

                    }
                    adapterFood.setList(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addValueEventListener(valueEventListener);
        query2.addValueEventListener(valueEventListener);
        query3.addValueEventListener(valueEventListener);



        binding.recFavFood.setHasFixedSize(true);
        binding.recFavFood.setAdapter(adapterFood);
        binding.recFavFood.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        return binding.getRoot();
    }


}