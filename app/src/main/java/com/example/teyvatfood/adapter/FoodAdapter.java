package com.example.teyvatfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodListViewHolder>{

    List<Food> listFood;
    private FoodListener foodListener;
    private Context context;

    public FoodAdapter(Context context) {
        this.context = context;
        listFood = new ArrayList<>();
    }

    public void setListFood(List<Food> listFood) {
        this.listFood = listFood;
        notifyDataSetChanged();
    }

    public void setFoodListener(FoodListener foodListener) {
        this.foodListener = foodListener;
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_in_list,parent,false);
        return new FoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListViewHolder holder, int position) {

        Food food = listFood.get(position);

        if(food != null){
            holder.foodName.setText(food.getFname());
            holder.foodPrice.setText("Price: "+food.getPrice()+" K");

            String img = food.getImage();
            String url = "192.168.1.185";
            String imgUrl = img.replace("localhost", url);
            Glide.with(context).load(imgUrl).into(holder.foodImg);
        }
    }

    @Override
    public int getItemCount() {
        if(listFood != null && listFood.size() > 0)
            return listFood.size();

        return 0;
    }

    public class FoodListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView foodImg;
        private TextView foodName, foodPrice;

        public FoodListViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImg = itemView.findViewById(R.id.item_img);
            foodName = itemView.findViewById(R.id.item_name);
            foodPrice = itemView.findViewById(R.id.item_price);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(foodListener!= null){
                foodListener.onFoodClick(view,getAdapterPosition());
            }
        }
    }

    public interface FoodListener{
        void onFoodClick(View view, int position);
    }
}
