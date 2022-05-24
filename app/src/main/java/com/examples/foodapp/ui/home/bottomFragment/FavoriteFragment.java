package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.examples.foodapp.adapters.AdapterFavFood;
import com.examples.foodapp.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFavoriteBinding binding =FragmentFavoriteBinding.inflate(inflater);
        AdapterFavFood adapterFood =new AdapterFavFood();
        ArrayList<String> list=new ArrayList<>();
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");
        list.add("Veggie tomato mix");

        adapterFood.setList(list);
        binding.recFavFood.setHasFixedSize(true);
        binding.recFavFood.setAdapter(adapterFood);
        binding.recFavFood.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


        return binding.getRoot();
    }
}