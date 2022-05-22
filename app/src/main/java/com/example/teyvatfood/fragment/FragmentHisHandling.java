package com.example.teyvatfood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.adapter.HistoryRecyclerViewAdapter;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHisHandling extends Fragment{

    private Account account;
    private List<Order> listOrder;

    private TextView tvTotal;
    private RecyclerView orderList;
    private HistoryRecyclerViewAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        account = activity.getAccount();

        return inflater.inflate(R.layout.fragment_his_handling,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotal = view.findViewById(R.id.handling_total);
        orderList = view.findViewById(R.id.handling_list);
        adapter = new HistoryRecyclerViewAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        orderList.setLayoutManager(manager);
        orderList.setAdapter(adapter);
        
        getOrderListAPI();
    }

    private void getOrderListAPI() {

        tvTotal.setText("Total: 0");

        ApiService.apiService.getOrderWithStatus(account.getId(), "Handling").enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()){
                    listOrder = response.body();
                    tvTotal.setText("Total: "+listOrder.size());
                    adapter.setListOrder(listOrder);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}
