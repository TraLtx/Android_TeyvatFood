package com.example.teyvatfood.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teyvatfood.R;
import com.example.teyvatfood.model.Order;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.OrderListViewHolder>{

    private List<Order> listOrder;

    public HistoryRecyclerViewAdapter() {
        listOrder = new ArrayList<>();
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
        notifyDataSetChanged();
        Log.e("ZJ","Set list order history: "+listOrder.size());
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_in_list,parent,false);
        return new OrderListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {

        Order order = listOrder.get(position);
        Log.e("ZJ","Get order in list at ("+position+") is"+ order.getId());


        if(order != null){
            holder.tvAmount.setText("Amount: "+order.getCart().getListFoodOrder().size());
            holder.tvPrice.setText("Price: "+order.getOrderPrice());
            holder.tvName.setText(order.getCusName());
            holder.tvPhone.setText(order.getCusPhone());
            holder.tvAddress.setText(order.getCusAddress());
            holder.tvStatus.setText("Status: "+order.getOrderStatus());
        }
    }

    @Override
    public int getItemCount() {
        if(listOrder != null && listOrder.size() > 0)
            return listOrder.size();

        return 0;
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder{

        private TextView tvAmount;
        private TextView tvPrice;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvStatus;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAmount = itemView.findViewById(R.id.order_amount);
            tvPrice = itemView.findViewById(R.id.order_price);
            tvName = itemView.findViewById(R.id.order_name);
            tvPhone = itemView.findViewById(R.id.order_phone);
            tvAddress = itemView.findViewById(R.id.order_address);
            tvStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
