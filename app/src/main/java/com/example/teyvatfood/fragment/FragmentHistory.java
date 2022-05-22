package com.example.teyvatfood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.adapter.HistoryViewPagerAdapter;
import com.example.teyvatfood.model.Account;
import com.google.android.material.tabs.TabLayout;

public class FragmentHistory extends Fragment {

    private Account account;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        account = activity.getAccount();

        View view = inflater.inflate(R.layout.fragment_history,container,false);
        tabLayout = view.findViewById(R.id.order_tab);
        viewPager = view.findViewById(R.id.order_viewpager);

        HistoryViewPagerAdapter adapter = new HistoryViewPagerAdapter(getChildFragmentManager(),4);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
