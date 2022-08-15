package com.capstone.fishku.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.fishku.data.model.FishCaught
import com.capstone.fishku.data.model.FishCaughtItem
import com.capstone.fishku.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarketViewModel : ViewModel() {

    val listFish = MutableLiveData<ArrayList<FishCaughtItem>>()

    fun setSearchFish(query: String){
        ApiConfig.run {
            getApiService()
                .getFishCaught(query)
                .enqueue(object: Callback<FishCaught>{
                    override fun onResponse(call: Call<FishCaught>, response: Response<FishCaught>) {
                        if (response.isSuccessful){
                            listFish.postValue(response.body()?.fishCaught)
                        }
                    }

                    override fun onFailure(call: Call<FishCaught>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                    }

                })
        }
    }

    fun getFishCaught(): LiveData<ArrayList<FishCaughtItem>>{
        return listFish
    }
}