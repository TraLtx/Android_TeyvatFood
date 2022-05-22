package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.teyvatfood.R;
import com.example.teyvatfood.adapter.CategoryAdapter;
import com.example.teyvatfood.adapter.FoodAdapter;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCategory extends AppCompatActivity implements CategoryAdapter.CategoryListener, FoodAdapter.FoodListener {

    private Account account;
    private Cart cart;

    private RecyclerView categoryList;
    private RecyclerView foodList;

    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;

    private ImageView btnBack;
    private TextView tvResult;
    private ProgressBar loadingBar;

    private int position;
    private List<Food> listFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        listFood = new ArrayList<>();
        init();
        setChoose(position);

        //Category recyclerView
        categoryAdapter = new CategoryAdapter();
        LinearLayoutManager categoryManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        categoryList.setLayoutManager(categoryManager);
        categoryList.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryListener(this);

        //ResultList recyclerView
        foodAdapter = new FoodAdapter(getApplicationContext());
        foodAdapter.setListFood(listFood);
        LinearLayoutManager foodListManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        foodList.setLayoutManager(foodListManager);
        foodList.setAdapter(foodAdapter);
        foodAdapter.setFoodListener(this);


        //Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setChoose(int position) {
        switch (position){
            case 0:
                tvResult.setText("Burger");
                getListFoodByCategory("Burger");
                break;
            case 1:
                tvResult.setText("Pizza");
                getListFoodByCategory("Pizza");
                break;
            case 2:
                tvResult.setText("Drink");
                getListFoodByCategory("Drink");
                break;
            case 3:
                tvResult.setText("Cake");
                getListFoodByCategory("Cake");
                break;
        }
    }

    private void getListFoodByCategory(String category){

        loadingBar.setVisibility(View.VISIBLE);
        ApiService.apiService.getFoodByType(category).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful()){
                    listFood = response.body();
                    foodAdapter.setListFood(listFood);
                    loadingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });
    }

    private void init(){

        Intent intent = getIntent();
        position = (int) intent.getSerializableExtra("position");
        account = (Account)intent.getSerializableExtra("account");
        cart = (Cart)intent.getSerializableExtra("cart");

        categoryList = findViewById(R.id.category_list);
        foodList = findViewById(R.id.category_food_list);
        btnBack = findViewById(R.id.category_back);
        tvResult = findViewById(R.id.result_name);
        loadingBar = findViewById(R.id.loading);
    }

    @Override
    public void onCategoryClick(View view, int position) {
        setChoose(position);
        foodAdapter.setListFood(listFood);
    }

    @Override
    public void onFoodClick(View view, int position) {
        Food food = listFood.get(position);
        Intent intent = new Intent(ViewCategory.this, FoodDetail.class);
        intent.putExtra("food", food);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
    }
}