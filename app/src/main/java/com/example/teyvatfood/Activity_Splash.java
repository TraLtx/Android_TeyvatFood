package com.example.teyvatfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.teyvatfood.activity.Login;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.dal.SQLiteHelper;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Splash extends AppCompatActivity {

    private List<Food> listSuggest;
    private Account acc;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadData();

        setContentView(R.layout.activity_splash);
    }

    private void loadData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                acc = db.getAccount();
                if(acc == null) gotoLogin();
                else getAccountAPI(acc);

            }
        }, 2500);
    }

    private void getAccountAPI(Account a) {
        ApiService.apiService.getAccount(a.getId()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    acc = response.body();

                    //Get Cart
                    cart = null;
                    getCartAPI();
                    Log.e("ZJ","Get cart for id:"+acc.getId());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    private void gotoMain(Account acc){
        final Intent mainIntent = new Intent(Activity_Splash.this, MainActivity.class);
        mainIntent.putExtra("account", acc);
        mainIntent.putExtra("cart", cart);
        startActivity(mainIntent);
        finish();
    }

    private void gotoLogin(){
        final Intent mainIntent = new Intent(Activity_Splash.this, Login.class);
        startActivity(mainIntent);
        finish();
    }

    private void getCartAPI() {
        ApiService.apiService.getCart(acc.getId()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    cart = response.body();
                    cart.setAccount(acc);
                    Log.e("ZJ","Get cart success: "+cart.getId());
                    if(cart == null) {
                        //Toast.makeText(getApplicationContext(),"Cart null",Toast.LENGTH_LONG);
                        Log.e("ZJ","Cart null");
                        createCartAPI();
                    }else{
                        gotoMain(acc);
                    }
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.e("ZJ","Get Cart Fail");
                createCartAPI();
            }
        });
    }

    private void createCartAPI(){
        ApiService.apiService.createCart(acc).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    cart = response.body();
                    Toast.makeText(getApplicationContext(),"Create cart",Toast.LENGTH_LONG);
                    gotoMain(acc);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

}