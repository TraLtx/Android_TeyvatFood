package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.teyvatfood.R;
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

public class Search extends AppCompatActivity implements FoodAdapter.FoodListener {

    private Account account;
    private Cart cart;

    private List<Food> searchResult;
    private String searchKey;

    private ImageView btnBack;
    private EditText eSearch;
    private ImageView btnSearch;
    private RecyclerView searchList;
    private ProgressBar searchLoad;
    private TextView tvSearchNoti;

    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

        adapter = new FoodAdapter(getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL, false);
        searchList.setLayoutManager(manager);
        searchList.setAdapter(adapter);
        adapter.setFoodListener(this);

        searchAPI();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKey = eSearch.getText().toString();
                searchAPI();
            }
        });
    }


    private void searchAPI(){

        searchLoad.setVisibility(View.VISIBLE);
        tvSearchNoti.setText("");

        ApiService.apiService.searchFood(searchKey).enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if(response.isSuccessful()){
                    searchResult = response.body();

                    Log.e("ZJ", "Search success: "+searchResult.size()+" items found");
                    searchLoad.setVisibility(View.GONE);
                    tvSearchNoti.setText("Found: "+ searchResult.size()+" items for '"+searchKey+"'!");
                    adapter.setListFood(searchResult);
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {

            }
        });
    }
    private void init(){

        Intent intent = getIntent();
        searchKey = (String) intent.getSerializableExtra("searchKey");
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");

        searchResult = new ArrayList<>();

        btnBack = findViewById(R.id.search_back);
        eSearch = findViewById(R.id.search_filed);
        btnSearch = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchLoad = findViewById(R.id.search_load);
        tvSearchNoti = findViewById(R.id.searchNoti);
    }

    @Override
    public void onFoodClick(View view, int position) {
        Food food = searchResult.get(position);
        Intent intent = new Intent(Search.this, FoodDetail.class);
        intent.putExtra("food", food);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
    }
}