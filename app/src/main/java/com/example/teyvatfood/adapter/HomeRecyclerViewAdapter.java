package com.example.teyvatfood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teyvatfood.R;
import com.example.teyvatfood.model.Food;

import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MainListViewHolder>{

    private List<Food> list;

    public HomeRecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Food> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_in_list,parent,false);
        return new MainListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListViewHolder holder, int position) {
        Food food = list.get(position);

        holder.tvName.setText(food.getFname());
        holder.tvPrice.setText(Float.toString(food.getPrice()));
        holder.tvCountry.setText(food.getCountry());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvPrice, tvCountry;
        private ImageView img;

        public MainListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.item_price);
            img = itemView.findViewById(R.id.item_img);
        }
    }
}
