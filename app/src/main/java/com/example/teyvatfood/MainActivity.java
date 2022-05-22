package com.example.teyvatfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.teyvatfood.activity.ManageAccount;
import com.example.teyvatfood.adapter.MainViewPagerAdapter;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private CardView ic_account;

    private Account account;
    private Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),4);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0: bottomNavigationView.getMenu().findItem(R.id.menuHome).setChecked(true); break;
                    case 1: bottomNavigationView.getMenu().findItem(R.id.menuFav).setChecked(true); break;
                    case 2: bottomNavigationView.getMenu().findItem(R.id.menuCart).setChecked(true); break;
                    case 3: bottomNavigationView.getMenu().findItem(R.id.menuHistory).setChecked(true); break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuHome: viewPager.setCurrentItem(0); break;
                    case R.id.menuFav: viewPager.setCurrentItem(1); break;
                    case R.id.menuCart: viewPager.setCurrentItem(2); break;
                    case R.id.menuHistory: viewPager.setCurrentItem(3); break;
                }
                return true;
            }
        });

        ic_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageAccount.class);
                intent.putExtra("account", account);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

    }

    public Account getAccount() {
        return account;
    }

    public Cart getCart(){
        return cart;
    }

    private void init(){
        viewPager = findViewById(R.id.main_viewPager);
        bottomNavigationView = findViewById(R.id.main_bottomMenu);
        ic_account = findViewById(R.id.ic_account);

        //Get Account & Cart
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");
        cart = (Cart) intent.getSerializableExtra("cart");
    }
}
//        Bundle bundle = new Bundle();
//        bundle.putString("edttext", "From Activity");
//// set Fragmentclass Arguments
//        Fragmentclass fragobj = new Fragmentclass();
//        fragobj.setArguments(bundle);