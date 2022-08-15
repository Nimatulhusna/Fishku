package com.capstone.fishku.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.capstone.fishku.data.model.Cart
import com.capstone.fishku.data.model.CartItem
import com.capstone.fishku.data.model.FishPreference
import com.capstone.fishku.data.retrofit.ApiConfig
import com.capstone.fishku.helper.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel(private val pref: FishPreference) : ViewModel() {
    private val _infoFish = MutableLiveData<Resource<String>>()
    val infoFish: LiveData<Resource<String>> = _infoFish

    fun fish(idFish: Int?, price: Int?, qty: String?){
        _infoFish.postValue(Resource.Loading())
        val client = ApiConfig.getApiService().cart(CartItem(idFish, price, qty))

        client.enqueue(object : Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                if (response.isSuccessful){
                    val cartResult = response.body()?.cart?.size

                    cartResult?.let { saveFish(it.toString()) }
                    _infoFish.postValue(Resource.Success(cartResult?.toString()))
                }else{
                    val errorResponse = Gson().fromJson(
                        response.errorBody()?.charStream(),
                        Resource::class.java
                    )
                    _infoFish.postValue(Resource.Error(errorResponse.message))
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Log.d(CartViewModel::class.java.simpleName, "onFailure Cart")
                _infoFish.postValue(Resource.Error(t.message))
            }

        })
    }

    fun getUser() = pref.getFish().asLiveData()

    private fun saveFish(key: String){
        viewModelScope.launch { pref.saveFish(key) }
    }

    private fun deleteFish(){
        viewModelScope.launch { pref.deleteFish() }
    }
}