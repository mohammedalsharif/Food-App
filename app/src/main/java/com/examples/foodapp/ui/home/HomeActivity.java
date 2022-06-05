package com.examples.foodapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.examples.foodapp.R;
import com.examples.foodapp.databinding.ActivityHomeBinding;
import com.examples.foodapp.ui.Login.LogInActivity;
import com.examples.foodapp.ui.Login.LoginFragment;
import com.examples.foodapp.ui.home.SlidingRootNav.menu.DrawerAdapter;
import com.examples.foodapp.ui.home.SlidingRootNav.menu.DrawerItem;
import com.examples.foodapp.ui.home.SlidingRootNav.menu.SimpleItem;
import com.examples.foodapp.ui.home.SlidingRootNav.menu.SpaceItem;
import com.examples.foodapp.ui.home.bottomFragment.CartFragment;
import com.examples.foodapp.ui.home.bottomFragment.FavoriteFragment;
import com.examples.foodapp.ui.home.bottomFragment.HomeFragment;
import com.examples.foodapp.ui.home.bottomFragment.UserFragment;
import com.examples.foodapp.utils.Methods;
import com.examples.foodapp.utils.MyReceiverInternetCase;
import com.examples.foodapp.utils.OnInternetCaseChange;
import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import io.ak1.OnBubbleClickListener;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    MyReceiverInternetCase receiverInternetCase;
    ActivityHomeBinding binding;
    private static final int POS_HOME = 0;
    private static final int POS_ACCOUNT = 1;
    private static final int POS_CART = 2;
    private static final int POS_FAVORITE = 3;
    private static final int POS_ORDERS =4;

    private static final int POS_LOGOUT = 5;


    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    SharedPreferences sp ;
    SharedPreferences.Editor editor ;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp=getSharedPreferences(LoginFragment.str_sp_checkBox, Context.MODE_PRIVATE);
        editor=sp.edit();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        receiverInternet();
        fm = getSupportFragmentManager();

        binding.bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int i) {
                switch (i){
                    case R.id.home:
                        Methods.replaceFragment(fm,R.id.container,new HomeFragment());
                 //       Toast.makeText(HomeActivity.this, i+"", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fav:
                        Methods.replaceFragment(fm,R.id.container,new FavoriteFragment());
                     //   Toast.makeText(HomeActivity.this, i+"", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user:
                        Methods.replaceFragment(fm,R.id.container,new UserFragment());
                      //  Toast.makeText(HomeActivity.this, i+"", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cart:
                        Methods.replaceFragment(fm, R.id.container, new CartFragment());
                        //  Toast.makeText(HomeActivity.this, i+"", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_ACCOUNT),
                createItemFor(POS_CART),
                createItemFor(POS_FAVORITE),
                createItemFor(POS_ORDERS),
                new SpaceItem(80),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    @Override
    public void onItemSelected(int position) {

        switch (position){
            case POS_HOME:
                binding.bubbleTabBar.setSelected(0,true);
                break;
                case POS_FAVORITE:
                binding.bubbleTabBar.setSelected(1,true);
                break;
              case POS_ACCOUNT:
                binding.bubbleTabBar.setSelected(2,true);
                break;
                case POS_CART:
                binding.bubbleTabBar.setSelected(3,true);
                break;
              case POS_LOGOUT+1:
                editor.putBoolean(LoginFragment.CHECK_BOX_STATE,false);
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
                finish();
                break;
        }
        slidingRootNav.closeMenu();


    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.black))
                .withSelectedTextTint(color(R.color.black));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {

                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiverInternetCase, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverInternetCase);
    }

    private void receiverInternet() {

        receiverInternetCase = new MyReceiverInternetCase(new OnInternetCaseChange() {
            @Override
            public void InternetCaseChange(boolean internetCase) {
                if (!internetCase) {
                    binding.connectionViewHome.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.connectionViewHome.getRoot().setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}


