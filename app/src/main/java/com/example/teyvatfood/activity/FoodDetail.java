package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;
import com.example.teyvatfood.model.FoodOrder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class FoodDetail extends AppCompatActivity {

    private ImageView btnBack;
    
    private ImageView foodImage;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvType;
    private TextView tvDescribe1;

    private ImageView plusBtn;
    private ImageView minusBtn;
    private TextView tvQuantityFood;

    private TextView tvFoodTotalPrice;
    private TextView btnAddToCart;

    private Account account;
    private Food food;
    private Cart cart;
    private int numQuantity;
    private int totalPrice;
    private int foodPrice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        
        init();
        
        setInfo();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numQuantity = numQuantity + 1;
                tvQuantityFood.setText(numQuantity+"");
                totalPrice = numQuantity * foodPrice;

                tvFoodTotalPrice.setText(totalPrice+" K");
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numQuantity > 1){
                    numQuantity = numQuantity - 1;
                    tvQuantityFood.setText(numQuantity+"");
                    totalPrice = numQuantity * foodPrice;

                    tvFoodTotalPrice.setText(totalPrice+" K");
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ZJ","Add to cart click");
                addtoCart();
                Toast.makeText(getApplicationContext(),"Add to cart success!",Toast.LENGTH_LONG);
            }
        });
    }

    private void addtoCart() {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setPrice(foodPrice);
        foodOrder.setQuatity(numQuantity);
        foodOrder.setFood(food);

        if(checkFoodExist(foodOrder)) return;

        foodOrder.setCartID(cart.getId());
        Toast.makeText(FoodDetail.this,"Add to cart success!",Toast.LENGTH_LONG).show();
        Log.e("ZJ","Call API");
        //Call API
        ApiService.apiService.addToCart(foodOrder).enqueue(new Callback<FoodOrder>() {
            @Override
            public void onResponse(Call<FoodOrder> call, Response<FoodOrder> response) {
                if(response.isSuccessful()){
                    FoodOrder repos = response.body();

                    List<FoodOrder> list = cart.getListFoodOrder();
                    if(list == null) list = new ArrayList<>();
                    list.add(repos);
                    cart.setListFoodOrder(list);

                    Toast.makeText(getApplicationContext(),"Add to cart success!",Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<FoodOrder> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Wrong!",Toast.LENGTH_LONG);
            }
        });
    }

    private Boolean checkFoodExist(FoodOrder foodOrder) {
        List<FoodOrder> listCheck = cart.getListFoodOrder();
        for(FoodOrder f : listCheck){
            if(foodOrder.getFood().getId() == f.getFood().getId())
                return true;
        }

        return false;
    }

    private void setInfo() {

        String img = food.getImage();
        String url = "192.168.1.185";
        String imgUrl = img.replace("localhost", url);
        Glide.with(FoodDetail.this).load(imgUrl).into(foodImage);

        tvName.setText(food.getFname());
        tvPrice.setText("Price: "+food.getPrice()+" K");
        tvType.setText("Type: "+food.getFtype());

        tvDescribe1.setText(food.getFdescribe());
    }

    private void init(){

        btnBack = findViewById(R.id.food_detail_back);

        foodImage = findViewById(R.id.detail_image);
        tvName = findViewById(R.id.food_detail_name);
        tvPrice = findViewById(R.id.food_detail_price);
        tvType = findViewById(R.id.food_detail_type);
        tvDescribe1 = findViewById(R.id.food_detail_desc1);

        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        tvQuantityFood = findViewById(R.id.quatityFood);

        tvFoodTotalPrice = findViewById(R.id.food_total_price);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);

        //Set default values
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");

        tvFoodTotalPrice.setText(food.getPrice()+" K");
        foodPrice = (int) food.getPrice();
        numQuantity = 1;

    }
}