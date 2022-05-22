package com.example.teyvatfood.adapter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.teyvatfood.fragment.FragmentHistory;
import com.example.teyvatfood.fragment.FragmentFavorite;
import com.example.teyvatfood.fragment.FragmentHome;
import com.example.teyvatfood.fragment.FragmentCart;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentFavorite();
            case 2: return new FragmentCart();
            case 3: return new FragmentHistory();
            default: return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
