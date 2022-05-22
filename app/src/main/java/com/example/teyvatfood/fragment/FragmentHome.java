package com.example.teyvatfood.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.teyvatfood.MainActivity;
import com.example.teyvatfood.R;
import com.example.teyvatfood.activity.FoodDetail;
import com.example.teyvatfood.activity.Search;
import com.example.teyvatfood.activity.ViewCategory;
import com.example.teyvatfood.adapter.BannerAdapter;
import com.example.teyvatfood.adapter.CategoryAdapter;
import com.example.teyvatfood.adapter.FoodAdapter;
import com.example.teyvatfood.api.ApiService;
import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FragmentHome extends Fragment implements CategoryAdapter.CategoryListener, FoodAdapter.FoodListener {

    private ViewPager bannerViewPager;
    private CircleIndicator circleIndicator;

    private EditText eSearch;
    private ImageView btn_search;
    private ProgressBar suggestLoad;
    private RecyclerView categoryList;
    private RecyclerView suggestList;

    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;

    private List<Food> listSuggest;
    private Account account;
    private Cart cart;

    private Handler bannerHandler = new Handler();
    private Runnable bannerRunable = new Runnable() {
        @Override
        public void run() {
            if(bannerViewPager.getCurrentItem() == 2){
                bannerViewPager.setCurrentItem(0);
            }else{
                bannerViewPager.setCurrentItem(bannerViewPager.getCurrentItem()+1);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        account = activity.getAccount();
        cart = activity.getCart();
        Log.e("ZJ", "Get cart - cart id: "+cart.getId());
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bannerViewPager = view.findViewById(R.id.banner_viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        BannerAdapter bannerAdapter = new BannerAdapter();
        bannerViewPager.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(bannerViewPager);

        bannerHandler.postDelayed(bannerRunable,3000);
        bannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                bannerHandler.removeCallbacks(bannerRunable);
                bannerHandler.postDelayed(bannerRunable,3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //init()
        btn_search = view.findViewById(R.id.home_seach_btn);
        eSearch = view.findViewById(R.id.home_search_filed);

        suggestLoad = view.findViewById(R.id.suggest_load);
        categoryList = view.findViewById(R.id.home_category_list);
        suggestList = view.findViewById(R.id.home_suggest_list);

        //Set category recyclerView
        categoryAdapter = new CategoryAdapter();
        LinearLayoutManager categoryManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        categoryList.setLayoutManager(categoryManager);
        categoryList.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryListener(this);

        //Set suggest recyclerView
        foodAdapter = new FoodAdapter(getContext());

        LinearLayoutManager suggestManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        suggestList.setLayoutManager(suggestManager);
        suggestList.setAdapter(foodAdapter);
        foodAdapter.setFoodListener(this);

        getSuggestList();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchKey = eSearch.getText().toString();

                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra("searchKey", searchKey);
                intent.putExtra("account", account);
                intent.putExtra("cart", cart);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //foodAdapter.setListFood(listSuggest);
        getSuggestList();
    }

    private void getSuggestList() {Log.e("ZJ", "GetSuggestList()");
        suggestLoad.setVisibility(View.VISIBLE);

        ApiService.apiService.getFoodSuggest().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    listSuggest = response.body();
                    foodAdapter.setListFood(listSuggest);

                    //Toast.makeText(getActivity(), "Loadlist Success ",Toast.LENGTH_LONG).show();
                    Log.e("ZJ", "Get list: " + listSuggest.size());
                    Log.e("ZJ", "GONE");
                    suggestLoad.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                Log.e("ZJ", "onFailure: "+t.toString());
                //Toast.makeText(getActivity(), "Load Failure",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Event Handle
    @Override
    public void onCategoryClick(View view, int position) {
        Log.e("ZJ", "Category Clicked!");
        Intent intent = new Intent(getContext(), ViewCategory.class);
        intent.putExtra("position", position);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
    }

    @Override
    public void onFoodClick(View view, int position) {
        Food food = listSuggest.get(position);
        Intent intent = new Intent(getActivity(), FoodDetail.class);
        intent.putExtra("food", food);
        intent.putExtra("account", account);
        intent.putExtra("cart", cart);
        startActivity(intent);
    }

}
