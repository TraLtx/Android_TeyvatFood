package com.example.teyvatfood.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teyvatfood.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryListViewHolder>{

    private CategoryListener categoryListener;

    public void setCategoryListener(CategoryListener categoryListener) {
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        return new CategoryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {

        switch (position){
            case 0:
                holder.catLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.color.category_1));
                holder.catTitle.setText("Burger");
                holder.catImg.setImageResource(R.drawable.category_burger);
                break;
            case 1:
                holder.catLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.color.category_2));
                holder.catTitle.setText("Pizza");
                holder.catImg.setImageResource(R.drawable.category_piza);
                break;
            case 2:
                holder.catLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.color.category_3));
                holder.catTitle.setText("Drink");
                holder.catImg.setImageResource(R.drawable.category_drink);
                break;
            case 3:
                holder.catLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.color.category_4));
                holder.catTitle.setText("Cake");
                holder.catImg.setImageResource(R.drawable.category_donut);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class CategoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView catImg;
        private TextView  catTitle;
        private LinearLayout catLayout;

        public CategoryListViewHolder(@NonNull View itemView){
            super(itemView);
            catImg = itemView.findViewById(R.id.cat_item_img);
            catTitle = itemView.findViewById(R.id.cat_item_title);
            catLayout = itemView.findViewById(R.id.category_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(categoryListener!= null){
                categoryListener.onCategoryClick(view,getAdapterPosition());
            }
        }
    }

    public interface CategoryListener{
        void onCategoryClick(View view,int position);
    }
}
