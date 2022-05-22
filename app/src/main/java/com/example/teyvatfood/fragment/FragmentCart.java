package com.example.teyvatfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.activity.Checkout;
import com.example.teyvatfood.adapter.FoodAdapter;
import com.example.teyvatfood.adapter.FoodOrderAdapter;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.FoodOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCart extends Fragment {

    private Cart cart;
    private Account account;

    private RecyclerView cartList;
    private FoodOrderAdapter adapter;

    private TextView tvAmount;
    private TextView tvOrderPrice;
    private TextView btnCheckout;
    private ProgressBar cart_loading;

    private List<FoodOrder> listFoodOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        cart = activity.getCart();
        account = activity.getAccount();

        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAmount = view.findViewById(R.id.amount_cart);
        tvOrderPrice = view.findViewById(R.id.order_total_price);
        btnCheckout = view.findViewById(R.id.btn_check_out);
        cart_loading = view.findViewById(R.id.cart_loading);

        cartList = view.findViewById(R.id.cart_list);
        adapter = new FoodOrderAdapter(getContext());

        listFoodOrder = cart.getListFoodOrder();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        cartList.setLayoutManager(manager);
        adapter.setListFoodOrder(listFoodOrder);
        cartList.setAdapter(adapter);

        setData(listFoodOrder);

        //Event here:

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.getListFoodOrder() != null && cart.getListFoodOrder().size() > 0){
                    Intent intent = new Intent(getContext(), Checkout.class);
                    intent.putExtra("account", account);
                    getCartAPI();
                    intent.putExtra("cart", cart);
                    int orderPrice = Integer.parseInt(tvOrderPrice.getText().toString());
                    intent.putExtra("orderPrice", orderPrice);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Your cart empty!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getCartAPI();
    }

    private void getCartAPI() {

        cart_loading.setVisibility(View.VISIBLE);;
        ApiService.apiService.getCart(account.getId()).enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if(response.isSuccessful()){
                    cart = response.body();
                    listFoodOrder = cart.getListFoodOrder();
                    adapter.setListFoodOrder(listFoodOrder);
                    setData(listFoodOrder);
                    cart_loading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }


    private void setData(List<FoodOrder> listFoodOrder) {
        if(listFoodOrder != null){
            tvAmount.setText("Amount: "+listFoodOrder.size());

            int totalPrice = 0;
            for(FoodOrder f : listFoodOrder){
                int price = (int) f.getPrice();
                totalPrice += price * f.getQuatity();
            }
            tvOrderPrice.setText(totalPrice+"");

            adapter.setPriceCart(totalPrice);
            adapter.setAmount(listFoodOrder.size());
        }else{
            tvAmount.setText("Amount: 0");
            tvOrderPrice.setText("0");
        }

        adapter.setTvPriceCart(tvOrderPrice);
        adapter.setTvAmount(tvAmount);
    }
}
