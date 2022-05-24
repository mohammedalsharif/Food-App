package com.examples.foodapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.foodapp.databinding.ActivityGetstarteedBinding;
import com.examples.foodapp.ui.Login.LogInActivity;
import com.examples.foodapp.ui.Login.LoginFragment;

public class GetstarteedActivity extends AppCompatActivity {
    private static final String GET_STARTED_STATE = "getStartedState";
    ActivityGetstarteedBinding binding;
    SharedPreferences sp ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGetstarteedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp=getSharedPreferences(LoginFragment.str_sp_checkBox, Context.MODE_PRIVATE);
        editor=sp.edit();
        Intent intent =new Intent(this, LogInActivity.class);
        if (sp.getBoolean(GET_STARTED_STATE,false)){
            startActivity(intent);
            finish();
        }
        binding.getStartedBtn.setOnClickListener(view -> {
            editor.putBoolean(GET_STARTED_STATE,true);
            editor.apply();
            startActivity(intent);
            finish();
        });
    }
}