package com.examples.foodapp.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Methods {

    public static void replaceFragment(FragmentManager fmManager, int container, Fragment fragment){
        fmManager.beginTransaction().replace(container,fragment).commit();
    }
}
