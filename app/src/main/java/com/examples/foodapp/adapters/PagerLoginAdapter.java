package com.examples.foodapp.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.examples.foodapp.model.TabFragment;

import java.util.ArrayList;

public class PagerLoginAdapter extends FragmentStatePagerAdapter {
    ArrayList<TabFragment> fragmentList;
    public PagerLoginAdapter(@NonNull FragmentManager fm, ArrayList<TabFragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position).getFragment();
    }


    @Override
    public int getCount() {
        return fragmentList!=null?fragmentList.size():null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTabName();
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
