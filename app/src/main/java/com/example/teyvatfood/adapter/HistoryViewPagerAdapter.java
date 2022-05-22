package com.example.teyvatfood.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.teyvatfood.fragment.FragmentHisAll;
import com.example.teyvatfood.fragment.FragmentHisHandling;
import com.example.teyvatfood.fragment.FragmentHisShipping;
import com.example.teyvatfood.fragment.FragmentHisSuccess;

public class HistoryViewPagerAdapter extends FragmentStatePagerAdapter {

    public HistoryViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHisHandling();
            case 1: return new FragmentHisShipping();
            case 2: return new FragmentHisSuccess();
            case 3: return new FragmentHisAll();
            default: return new FragmentHisHandling();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Hanlding";
            case 1: return "Shipping";
            case 2: return "Success";
            case 3: return "All";
            default: return "Hanlding";
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
