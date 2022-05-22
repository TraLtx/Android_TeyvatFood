package com.example.teyvatfood.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.FoodOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodOrderAdapter extends RecyclerView.Adapter<FoodOrderAdapter.FoodOrderViewHolder>{

    private List<FoodOrder> listFoodOrder;
    private Context context;
    private TextView tvPriceCart;
    private TextView tvAmount;
    private int priceCart;
    private int amount;

    public FoodOrderAdapter(Context context) {
        this.context = context;
        listFoodOrder = new ArrayList<>();
    }

    public void setListFoodOrder(List<FoodOrder> listFoodOrder) {
        this.listFoodOrder = listFoodOrder;
        notifyDataSetChanged();
    }

    public void setTvPriceCart(TextView tvPriceCart) {
        this.tvPriceCart = tvPriceCart;
    }

    public void setTvAmount(TextView tvAmount) {
        this.tvAmount = tvAmount;
    }

    public void setPriceCart(int priceCart) {
        this.priceCart = priceCart;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_in_cart,parent,false);
        return new FoodOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOrderViewHolder holder, int position) {

        FoodOrder foodOrder = listFoodOrder.get(position);

        final int[] price = {(int) foodOrder.getPrice()};
        final int[] totalPrice = {foodOrder.getQuatity() * price[0]};

        if(foodOrder != null){
            holder.tvName.setText(foodOrder.getFood().getFname());
            holder.tvQuantity.setText(""+foodOrder.getQuatity());
            holder.tvPrice.setText("Price: " + totalPrice[0] +" K");
            String img = foodOrder.getFood().getImage();
            String url = "192.168.1.185";
            String imgUrl = img.replace("localhost", url);
            Glide.with(context).load(imgUrl).into(holder.image);
        }

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = foodOrder.getQuatity();
                quantity = quantity + 1;
                foodOrder.setQuatity(quantity);

                totalPrice[0] = (int) (totalPrice[0] + foodOrder.getPrice());

                holder.tvQuantity.setText(""+foodOrder.getQuatity());
                holder.tvPrice.setText("Price: " + totalPrice[0] +" K");

                updateFoodOrderAPI(foodOrder);

                priceCart += foodOrder.getPrice();
                tvPriceCart.setText(priceCart+"");
            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = foodOrder.getQuatity();
                if(quantity > 1){
                    quantity = quantity - 1;
                    foodOrder.setQuatity(quantity);

                    totalPrice[0] = (int) (totalPrice[0] - foodOrder.getPrice());

                    holder.tvQuantity.setText(""+foodOrder.getQuatity());
                    holder.tvPrice.setText("Price: " + totalPrice[0] +" K");

                    updateFoodOrderAPI(foodOrder);
                    priceCart -= foodOrder.getPrice();
                    tvPriceCart.setText(priceCart+"");
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFoodOrderAPI(foodOrder);
            }
        });
    }

    private void deleteFoodOrderAPI(FoodOrder foodOrderRemove) {
        notifyDataSetChanged();
        Toast.makeText(context,"Food removed!",Toast.LENGTH_LONG).show();

        ApiService.apiService.removeFoodOrder(foodOrderRemove).enqueue(new Callback<FoodOrder>() {
            @Override
            public void onResponse(Call<FoodOrder> call, Response<FoodOrder> response) {
                if(response.isSuccessful()){
                    listFoodOrder.remove(foodOrderRemove);
                    int p = (int) foodOrderRemove.getPrice();
                    int priceToSub = p * foodOrderRemove.getQuatity();
                    priceCart = priceCart - priceToSub;
                    tvPriceCart.setText(priceCart+"");
                    amount = amount - 1;
                    tvAmount.setText("Amount: "+amount);
                }
            }

            @Override
            public void onFailure(Call<FoodOrder> call, Throwable t) {

            }
        });
    }

    private void updateFoodOrderAPI(FoodOrder foodOrder) {
        Log.e("ZJ","Call API Update amount");
        ApiService.apiService.updateFoodOrder(foodOrder).enqueue(new Callback<FoodOrder>() {
            @Override
            public void onResponse(Call<FoodOrder> call, Response<FoodOrder> response) {
                if(response.isSuccessful()) Log.e("ZJ","Call API Update Success!");
            }

            @Override
            public void onFailure(Call<FoodOrder> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(listFoodOrder != null && listFoodOrder.size() > 0) return listFoodOrder.size();
        return 0;
    }

    public class FoodOrderViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView tvName;
        private TextView tvPrice;


        private TextView tvQuantity;
        private ImageView plusBtn;
        private ImageView minusBtn;

        private TextView btnRemove;

        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_img);
            tvName = itemView.findViewById(R.id.item_name);
            tvPrice = itemView.findViewById(R.id.item_price);

            tvQuantity = itemView.findViewById(R.id.quatity_Food);
            plusBtn = itemView.findViewById(R.id.plus_Btn);
            minusBtn = itemView.findViewById(R.id.minus_Btn);

            btnRemove = itemView.findViewById(R.id.btn_remove_cart);
        }
    }
}
