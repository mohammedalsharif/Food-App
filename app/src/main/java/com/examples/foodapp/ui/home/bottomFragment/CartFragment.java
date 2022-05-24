package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.examples.foodapp.adapters.AdapterCart;
import com.examples.foodapp.databinding.FragmentCartBinding;

import java.util.ArrayList;

public class CartFragment extends Fragment {


    public CartFragment() {
        // Required empty public constructor
    }


    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentCartBinding binding = FragmentCartBinding.inflate(inflater);
        AdapterCart adapterCart = new AdapterCart();
        ArrayList<String> listCart = new ArrayList<>();
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        listCart.add("Food");
        adapterCart.setList(listCart);
        binding.recCart.setAdapter(adapterCart);
        binding.recCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recCart.setHasFixedSize(true);
        return binding.getRoot();
    }
}