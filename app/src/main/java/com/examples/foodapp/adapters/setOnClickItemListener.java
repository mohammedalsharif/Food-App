package com.examples.foodapp.adapters;

import android.view.View;
import android.widget.TextView;

import com.examples.foodapp.model.Food;

public interface setOnClickItemListener {
    void OnClickItemListener(Food food, View card , TextView tvName, TextView tvPrice);
}
