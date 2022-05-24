package com.examples.foodapp.ui.home.ProductsFragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.adapters.AdapterFood;
import com.examples.foodapp.adapters.setOnClickItemListener;
import com.examples.foodapp.databinding.FragmentFoodBinding;
import com.examples.foodapp.model.Food;
import com.examples.foodapp.ui.DetailsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FoodFragment extends Fragment {

    private DatabaseReference dbRef;
    private String reference = "Foods";

    public FoodFragment() {
        // Required empty public constructor
    }

    public void setReference(String reference) {
        this.reference = reference;
        dbRef = FirebaseDatabase.getInstance().getReference(this.reference);
    }

    public static FoodFragment newInstance(String reference) {
        FoodFragment fragment = new FoodFragment();
        fragment.setReference(reference);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFoodBinding binding = FragmentFoodBinding.inflate(inflater);
        ArrayList<Food> listFood = new ArrayList<>();
        AdapterFood adapterFood = new AdapterFood(getContext(),new setOnClickItemListener() {
            @Override
            public void OnClickItemListener(Food food, View card, TextView tvName, TextView tvPrice) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Food", food);

                Pair[] pairs = new Pair[3];

                pairs[0] = new Pair<View, String>(card, "cardTransition");
                pairs[1] = new Pair<View, String>(tvName, "nameTransition");
                pairs[2] = new Pair<View, String>(tvPrice, "priceTransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);

                startActivity(intent, options.toBundle());
            }
        });
        adapterFood.setListFood(listFood);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFood.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food foods = dataSnapshot.getValue(Food.class);
                    listFood.add(foods);
                }

                adapterFood.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Foods", "onCancelled: " + error.getMessage());

            }
        });


        binding.recOrder.setAdapter(adapterFood);
        binding.recOrder.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.recOrder.setHasFixedSize(true);


        return binding.getRoot();
    }

}