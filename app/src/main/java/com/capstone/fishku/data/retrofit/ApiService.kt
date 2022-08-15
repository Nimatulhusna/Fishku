package com.capstone.fishku.data.retrofit

import com.capstone.fishku.data.model.*
import com.capstone.fishku.helper.NetworkInfo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/api/register")
    fun register (@Body request: User): Call<Register>

    @POST("/api/login")
    fun login (@Body request: UserLogin): Call<Login>

    @POST("/api/cart")
    fun cart(@Body request: CartItem): Call<Cart>

    @POST("/api/order")
    fun order(@Body request: OrderingItem): Call<Ordering>

    @POST("/api/history")
    fun history(@Body request: HistoryItem): Call<History>

    @POST("/predict")
    fun predict()

    @GET("/api/profile/:email")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getProfile(): Call<DataItem>

    @GET("/api/fish_caught")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getFishCaught(query: String): Call<FishCaught>

    @GET("/api/fish_caught/:fish_name")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getFishDetail(@Path("fish_name") fish_name: String): Call<FishCaughtItem>

    @GET("/api/fish_caught/:fish_name")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getSearchFish(@Query("q") query: String): Call<FishCaught>

    @GET("/api/cart")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getCart(): Call<Cart>

    @GET("/api/total_price")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getTotalPrice( @Path("total_price") total_price: Int): Call<Cart>

    @GET("/api/order/:id_cart")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getDetailOrder(@Path("id_cart") id_cart: Int): Call<Ordering>

    @GET("/api/history/:id_history")
    @Headers("Authorization: ${NetworkInfo.API_URL}")
    fun getHistory(@Path("id_history") id_history: Int): Call<History>
}