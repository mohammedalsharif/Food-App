package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.examples.foodapp.adapters.AdapterCart;
import com.examples.foodapp.adapters.OnClickRemoveItem;
import com.examples.foodapp.databinding.FragmentCartBinding;
import com.examples.foodapp.model.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment implements OnClickRemoveItem {
    private DatabaseReference dbRef;

    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference("listCart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCartBinding binding = FragmentCartBinding.inflate(inflater);
        AdapterCart adapterCart = new AdapterCart(this);
        ArrayList<Food> listCart = new ArrayList<>();
        adapterCart.setList(listCart);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCart.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food foods = dataSnapshot.getValue(Food.class);
                    foods.setId(dataSnapshot.getKey());
                    listCart.add(foods);
                }

                adapterCart.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Foods", "onCancelled: " + error.getMessage());

            }
        });

        binding.recCart.setAdapter(adapterCart);
        binding.recCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recCart.setHasFixedSize(true);
        return binding.getRoot();
    }

    @Override
    public void ItemClickRemove(String IdFood) {
      dbRef.child(IdFood).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void unused) {
              Toast.makeText(getContext(), "Remove Item Success", Toast.LENGTH_SHORT).show();
          }
      });
    }
}