package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Address;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Information;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeAccountInfo extends AppCompatActivity {

    private Account account;
    private Cart cart;

    private EditText eName;
    private EditText eUsername;
    private EditText ePassword;
    private EditText ePhone;
    private EditText eCity;
    private EditText eStreet;
    private EditText eDistrict;
    private EditText eNumHouse;

    private ImageView btnBack;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_account_info);

        init();

        setData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAccountAPI();
            }
        });
    }

    private void saveAccountAPI() {
        Log.e("ZJ", "saveAccountAPI ");
        Information information = account.getInfomation();
        String name = eName.getText().toString();
        String phone = ePhone.getText().toString();
        information.setName(name);
        information.setPhone(phone);

        Address address = account.getAddress();
        String city = eCity.getText().toString();
        String district = eDistrict.getText().toString();
        String street = eStreet.getText().toString();
        String numHouse = eNumHouse.getText().toString();
        address.setCity(city);
        address.setDistrict(district);
        address.setStreet(street);
        address.setNumHouse(numHouse);

        String username = eUsername.getText().toString();
        String password = ePassword.getText().toString();
        account.setUsername(username);
        account.setPassword(password);
        account.setInfomation(information);
        account.setAddress(address);

        ApiService.apiService.updateAccount(account).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account newAcc = response.body();

                    //Update account success then go to ManageAccount activity
                    Intent intent = new Intent(ChangeAccountInfo.this, MainActivity.class);
                    intent.putExtra("account", newAcc);
                    intent.putExtra("cart", cart);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });
    }

    private void setData() {
        eName.setText(account.getInfomation().getName());
        eUsername.setText(account.getUsername());
        ePassword.setText(account.getPassword());
        ePhone.setText(account.getInfomation().getPhone());
        eCity.setText(account.getAddress().getCity());
        eDistrict.setText(account.getAddress().getDistrict());
        eStreet.setText(account.getAddress().getStreet());
        eNumHouse.setText(account.getAddress().getNumHouse());
    }

    private void init(){

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");

        eName = findViewById(R.id.acc_name);
        eUsername = findViewById(R.id.acc_username);
        ePassword = findViewById(R.id.acc_password);
        ePhone = findViewById(R.id.acc_phone);
        eCity = findViewById(R.id.acc_city);
        eStreet = findViewById(R.id.acc_street);
        eDistrict = findViewById(R.id.acc_dictrict);
        eNumHouse = findViewById(R.id.acc_numhouse);

        btnBack = findViewById(R.id.change_info_back);
        btnSave = findViewById(R.id.save_btn);
    }
}