package com.example.teyvatfood.api;

import android.database.Observable;

import com.example.teyvatfood.model.Account;
import com.example.teyvatfood.model.Cart;
import com.example.teyvatfood.model.Food;
import com.example.teyvatfood.model.FoodOrder;
import com.example.teyvatfood.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    String localhost = "192.168.1.185";

    String url = "http://"+localhost+":8080/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiService.class);



    //Register
    //http://localhost:8080/api/accounts/register
    @POST("api/accounts/register")
    Call<Account> register(@Body Account acc);

    //Login
    //http://localhost:8080/api/accounts/login?username=cus1&password=1
    @GET("api/accounts/login")
    Call<Account> login(@Query("username") String username, @Query("password") String password);

    //Get account by ID
    ///http://localhost:8080/api/accounts/getAccount?id=1
    @GET("api/accounts/getAccount")
    Call<Account> getAccount(@Query("id") int id);

    //Manage account
    //http://localhost:8080/api/accounts/update
    @PUT("api/accounts/update")
    Call<Account> updateAccount(@Body Account account);

    //Get food suggest list
    //http://localhost:8080/api/foods/suggest
    @GET("api/foods/suggest")
    Call<List<Food>> getFoodSuggest();

    //Search by name
    //http://localhost:8080/api/foods/search
    @GET("api/foods/search")
    Call<List<Food>> searchFood(@Query("key") String key);

    //Get food by type
    //http://localhost:8080/api/foods?type=
    @GET("api/foods/type")
    Call<List<Food>> getFoodByType(@Query("type") String type);

    //Get cart for account
    //http://localhost:8080/api/cart/account?id=1
    @GET("api/cart/account")
    Call<Cart> getCart(@Query("id") int accountID);

    //Create cart
    //http://localhost:8080/api/cart/create
    @POST("api/cart/create")
    Call<Cart> createCart(@Body Account account);

    //Add to cart = Update cart
    //http://localhost:8080/api/cart/update
//    @PUT("api/cart/update")
//    Call<Cart> updateCart(@Body Cart cart);

    //Add to cart = add new foodOrder
    //http://localhost:8080/api/foodOrder/add-to-cart
    @POST("api/foodOrder/add-to-cart")
    Call<FoodOrder> addToCart(@Body FoodOrder foodOrder);

    //Update amount in cart list
    //http://localhost:8080/api/foodOrder/update
    @PUT("api/foodOrder/update")
    Call<FoodOrder> updateFoodOrder(@Body FoodOrder foodOrder);

    //Remove fooOrder in cart
    //http://localhost:8080/api/foodOrder/remove
    @POST("api/foodOrder/remove")
    Call<FoodOrder> removeFoodOrder(@Body FoodOrder foodOrder);
//    @HTTP(method = "DELETE", path = "api/foodOrder/remove", hasBody = true)
//    Observable<Void> removeFoodOrder(@Body FoodOrder foodOrder);

    //Order
    //http://localhost:8080/api/order/create
    @POST("api/order/create")
    Call<Order> createOrder(@Body Order order);

    //Set cart ordered
    //http://localhost:8080/api/cart/ordered
    @PUT("api/cart/ordered")
    Call<Cart> setCartOrdered(@Body Cart cart);

    //Get ALL list Order
    //http://localhost:8080/api/order/forAccount
    @GET("api/order/forAccount")
    Call<List<Order>> getAllOrder(@Query("accountID") int accountID);

    //Get status list Order
    //http://localhost:8080/api/order/forAccountStatus
    @GET("api/order/forAccountStatus")
    Call<List<Order>> getOrderWithStatus(@Query("accountID") int accountID,@Query("status") String status);
}
