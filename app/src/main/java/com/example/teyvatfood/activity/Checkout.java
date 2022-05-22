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

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Order;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity {

    private Account account;
    private Cart cart;
    private int orderPrice;

    private TextView tvName;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvAmount;
    private TextView tvPrice;

    private ImageView btnBack;
    private Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        init();

        setData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ZJ","Click Order");
                String createDate = "getDate";
                String cusName = tvName.getText().toString();
                String cusPhone = tvPhone.getText().toString();
                String cusAddress = tvAddress.getText().toString();

                Order order = new Order();
                order.setCreateDate(createDate);
                order.setCusName(cusName);
                order.setCusPhone(cusPhone);
                order.setCusAddress(cusAddress);
                order.setOrderPrice(orderPrice);
                order.setOrderStatus("Handling");
                order.setCart(cart);
                order.setAccount(account);

                createOrderAPI(order);
            }
        });
    }

    //Create new Order and get Response Order
    private void createOrderAPI(Order order) {Log.e("ZJ","Call create Order API");
        ApiService.apiService.createOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()){
                    Order orderRes = response.body();
                    //Set Cart of this Order to "ordered"
                    setCartOrdered(orderRes.getCart());
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    //Set Cart of this Order to "ordered"
    private void setCartOrdered(Cart cart) {Log.e("ZJ","Call set Cart API");
        cart.setStatus("ordered");
        ApiService.apiService.setCartOrdered(cart).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()) {
                    //Create new Cart(null)  for Account
                    createCartAPI();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

    //Create new Cart(null)  for Account
    private void createCartAPI(){Log.e("ZJ","Call create Cart API");
        ApiService.apiService.createCart(account).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    Cart cartNew = response.body();
                    //Then go to MainActivity
                    Intent intent = new Intent(Checkout.this, MainActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("cart", cartNew);
                    startActivity(intent);
                    Log.e("ZJ","Done, finish()!");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

    private void setData(){
        tvName.setText("Name: "+account.getInfomation().getName());
        tvPhone.setText("Phone: "+account.getInfomation().getPhone());
        String address = account.getAddress().getNumHouse()+", "
                        +account.getAddress().getStreet()+", "
                        +account.getAddress().getDistrict()+", "
                        +account.getAddress().getCity();
        tvAddress.setText("Address: "+address);

        int amount = cart.getListFoodOrder().size();
        tvAmount.setText("Amount: "+amount);
        tvPrice.setText("Total price: "+orderPrice);
    }
    private void init(){
        //Get Account & Cart
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");
        orderPrice = (int) intent.getSerializableExtra("orderPrice");

        tvName = findViewById(R.id.checkout_name);
        tvPhone = findViewById(R.id.checkout_phone);
        tvAddress = findViewById(R.id.checkout_address);
        tvAmount = findViewById(R.id.checkout_amount);
        tvPrice = findViewById(R.id.checkout_price);

        btnBack = findViewById(R.id.checkout_back);
        btnOrder = findViewById(R.id.order_btn);
    }
}


//    private void getCartAPI() {
//        ApiService.apiService.getCart(account.getId()).enqueue(new Callback<Cart>() {
//            @Override
//            public void onResponse(Call<Cart> call, Response<Cart> response) {
//                if(response.isSuccessful()){
//                    Cart cartNew = response.body();
//                    cartNew.setAccount(account);
//                    Log.e("ZJ","Get cart success: "+cart.getId());
//                    if(cart == null) {
//                        //Toast.makeText(getApplicationContext(),"Cart null",Toast.LENGTH_LONG);
//                        Log.e("ZJ","Cart null");
//                        createCartAPI();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Cart> call, Throwable t) {
//
//            }
//        });
//    }