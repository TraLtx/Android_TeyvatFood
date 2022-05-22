package com.example.teyvatfood.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.teyvatfood.R;

public class BannerAdapter extends PagerAdapter {

    private int[] img = {R.drawable.banner_1,R.drawable.banner_2,R.drawable.banner_3};

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_image,container,false);

        ImageView imgBanner = view.findViewById(R.id.img_banner);
        imgBanner.setImageResource(img[position]);

        //add view
        container.addView(view);

        return view;
    }



    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
