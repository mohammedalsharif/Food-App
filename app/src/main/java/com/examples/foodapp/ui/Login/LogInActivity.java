package com.examples.foodapp.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;

import com.examples.foodapp.R;
import com.examples.foodapp.adapters.PagerLoginAdapter;
import com.examples.foodapp.databinding.ActivityLoginBinding;
import com.examples.foodapp.model.TabFragment;
import com.examples.foodapp.ui.home.HomeActivity;
import com.examples.foodapp.utils.MyReceiverInternetCase;
import com.examples.foodapp.utils.OnInternetCaseChange;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ArrayList<TabFragment> fragmentList;
    PagerLoginAdapter adapterPager;
    MyReceiverInternetCase receiverInternetCase;
    SharedPreferences sp ;
    SharedPreferences.Editor editor ;
    FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        sp=getSharedPreferences(LoginFragment.str_sp_checkBox, Context.MODE_PRIVATE);
        editor=sp.edit();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tabLayout.setupWithViewPager(binding.viewpagerLogin);
        fragmentList = new ArrayList<>();

        receiverInternet();
        //    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        checkedKeyboard();
        adapterPager = new PagerLoginAdapter(getSupportFragmentManager(), fragmentList);

        fragmentList.add(new TabFragment(getString(R.string.str_login), LoginFragment.newInstance(new backPagerListener() {
            @Override
            public void onBackPager(View view) {
                binding.viewpagerLogin.setCurrentItem(1, true);
            }
        })));

        fragmentList.add(new TabFragment(getString(R.string.str_signUp), SignUpFragment.newInstance(new backPagerListener() {
            @Override
            public void onBackPager(View view) {
                binding.viewpagerLogin.setCurrentItem(0, true);
            }
        })));

        binding.viewpagerLogin.setAdapter(adapterPager);

    }

    private void receiverInternet() {

        receiverInternetCase = new MyReceiverInternetCase(new OnInternetCaseChange() {
            @Override
            public void InternetCaseChange(boolean internetCase) {
                if (!internetCase) {
                    binding.connectionView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.connectionView.getRoot().setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiverInternetCase, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        if (sp.getBoolean(LoginFragment.CHECK_BOX_STATE,false)){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                 finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverInternetCase);
    }



    private void checkedKeyboard() {
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = binding.getRoot().getRootView().getHeight() - binding.getRoot().getHeight();
                if (heightDiff > dpToPx(getBaseContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                    // ... do something here
                    binding.CardView.setVisibility(View.GONE);

                } else {
                    binding.CardView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }


}