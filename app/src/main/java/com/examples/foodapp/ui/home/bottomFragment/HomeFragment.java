package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.examples.foodapp.R;
import com.examples.foodapp.adapters.PagerLoginAdapter;
import com.examples.foodapp.model.TabFragment;
import com.examples.foodapp.databinding.FragmentTextBinding;
import com.examples.foodapp.ui.home.ProductsFragment.FoodFragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";


    public static HomeFragment createFor(String text) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTextBinding binding = FragmentTextBinding.inflate(inflater);
        binding.foodTab.setupWithViewPager(binding.foodViewpager);
        ArrayList<TabFragment> tabFragments = new ArrayList<>();
        tabFragments.add(new TabFragment(getString(R.string.str_foods), FoodFragment.newInstance("Foods")));
        tabFragments.add(new TabFragment(getString(R.string.str_drins), FoodFragment.newInstance("Drinks")));
        tabFragments.add(new TabFragment(getString(R.string.str_snacks), FoodFragment.newInstance("Snacks")));
      //  tabFragments.add(new TabFragment("Sauce", FoodFragment.newInstance("Foods")));
        PagerLoginAdapter pagerLoginAdapter = new PagerLoginAdapter(getActivity().getSupportFragmentManager(), tabFragments);
        binding.foodViewpager.setAdapter(pagerLoginAdapter);


//
//
        return binding.getRoot();

    }

}
