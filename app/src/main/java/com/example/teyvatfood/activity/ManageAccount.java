package com.example.teyvatfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teyvatfood.Activity_Splash;
import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.dal.SQLiteHelper;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;

public class ManageAccount extends AppCompatActivity {

    private Account account;
    private Cart cart;

    private TextView tvName;
    private TextView tvUsername;
    private TextView tvPhone;
    private TextView tvAddress;

    private ImageView btnBack;
    private Button btnLogout;
    private Button btnChangeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        init();

        setData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                db.logout(account.getId());

                gotoLoginAcivity();
            }
        });

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoChangeInfo();
            }
        });
    }

    private void gotoChangeInfo() {
        Intent intent = new Intent(ManageAccount.this, ChangeAccountInfo.class);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
    }

    private void gotoLoginAcivity() {
        final Intent mainIntent = new Intent(ManageAccount.this, Login.class);
        startActivity(mainIntent);
        finish();
    }

    private void setData() {
        tvName.setText(account.getInfomation().getName());
        tvUsername.setText(account.getUsername());
        tvPhone.setText(account.getInfomation().getPhone());

        String address = account.getAddress().getNumHouse()+", "
                +account.getAddress().getStreet()+", "
                +account.getAddress().getDistrict()+", "
                +account.getAddress().getCity();
        tvAddress.setText(address);
    }

    private void init(){
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");

        tvName = findViewById(R.id.name);
        tvUsername = findViewById(R.id.username);
        tvPhone = findViewById(R.id.phone);
        tvAddress = findViewById(R.id.address);

        btnBack = findViewById(R.id.account_back);
        btnLogout = findViewById(R.id.logout);
        btnChangeInfo = findViewById(R.id.btn_change_info);
    }
}