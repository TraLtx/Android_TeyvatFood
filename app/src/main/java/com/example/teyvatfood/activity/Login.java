package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.dal.SQLiteHelper;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText eUsername, ePassword;
    private Button btn_login;
    private TextView btn_tnRegister;

    private Account account;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = eUsername.getText().toString();
                String password = ePassword.getText().toString();

                account = null;

                loginAPI(username,password);


            }
        });

        btn_tnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginAPI(String username, String password) {
        ApiService.apiService.login(username,password).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    account = response.body();
                    Toast.makeText(Login.this, "Hello "+account.getUsername(),Toast.LENGTH_LONG).show();

                    addAccountToDB(account);

                    //Get Cart
                    cart = null;
                    getCartAPI();
                    Log.e("ZJ","Get cart for id:"+account.getId());

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(Login.this, t.toString(),Toast.LENGTH_LONG).show();
                Log.i("TTTTTTTTTTTTTTTTTTTTT", "onFailure: "+t.toString());
            }
        });
    }

    private void addAccountToDB(Account account) {
        Account acc = new Account(account.getId(), account.getUsername(), account.getPassword());
        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        db.loginSuccess(acc);
    }

    private void gotoMain() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
        finish();
    }

    private void init(){
        eUsername = findViewById(R.id.login_username);
        ePassword = findViewById(R.id.login_password);

        btn_login = findViewById(R.id.login_btn);
        btn_tnRegister = findViewById(R.id.login_register);
    }
    private void getCartAPI() {
        ApiService.apiService.getCart(account.getId()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    cart = response.body();
                    cart.setAccount(account);
                    Log.e("ZJ","Get cart success: "+cart.getId());
                    gotoMain();
                    if(cart == null) {
                        //Toast.makeText(getApplicationContext(),"Cart null",Toast.LENGTH_LONG);
                        Log.e("ZJ","Cart null");
                        createCartAPI();
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
        ApiService.apiService.createCart(account).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    cart = response.body();
                    Toast.makeText(getApplicationContext(),"Create cart",Toast.LENGTH_LONG);
                    gotoMain();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

}