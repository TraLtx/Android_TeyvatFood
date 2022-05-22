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

import com.example.teyvatfood.R;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Address;
import com.example.teyvatfood.model.Information;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private EditText eName, eUsername, ePassword;
    private EditText ePhone, eCity, eDistrict, eStreet, eNumHouse;
    private Button btnSignup;
    TextView tv_btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Information information = new Information();
                String name = eName.getText().toString();
                String phone = ePhone.getText().toString();
                information.setName(name);
                information.setPhone(phone);

                Address address = new Address();
                String city = eCity.getText().toString();
                String district = eDistrict.getText().toString();
                String street = eStreet.getText().toString();
                String numHouse = eNumHouse.getText().toString();
                address.setCity(city);
                address.setDistrict(district);
                address.setStreet(street);
                address.setNumHouse(numHouse);

                Account account = new Account();
                String username = eUsername.getText().toString();
                String password = ePassword.getText().toString();
                account.setUsername(username);
                account.setPassword(password);
                account.setInfomation(information);
                account.setAddress(address);

                register(account);

                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        tv_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoLoginPage();
            }
        });
    }

    private void gotoLoginPage() {
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void init(){
        eName = findViewById(R.id.reg_name);
        eUsername = findViewById(R.id.reg_username);
        ePassword = findViewById(R.id.reg_password);

        ePhone = findViewById(R.id.reg_phone);
        eCity = findViewById(R.id.reg_city);
        eDistrict = findViewById(R.id.reg_dictrict);
        eStreet = findViewById(R.id.reg_street);
        eNumHouse = findViewById(R.id.reg_numhouse);

        btnSignup = findViewById(R.id.reg_btn);
        tv_btnLogin = findViewById(R.id.reg_login);
    }

    private void register(Account acc){
        ApiService.apiService.register(acc).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                Toast.makeText(SignUp.this, "Register Success!",Toast.LENGTH_LONG).show();
                gotoLoginPage();
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(SignUp.this, t.toString(),Toast.LENGTH_LONG).show();
                Log.i("TTTTTTTTTTTTTTTTTTTTT", "onFailure: "+t.toString());
            }
        });
    }
}