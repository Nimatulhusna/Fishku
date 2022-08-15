package com.capstone.fishku.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.fishku.data.model.FishCaughtItem
import com.capstone.fishku.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val fish = MutableLiveData<FishCaughtItem>()

    fun setFishDetail(fishName: String?){
        ApiConfig.getApiService()
            .getFishDetail(fishName.toString())
            .enqueue(object : Callback<FishCaughtItem>{
                override fun onResponse(
                    call: Call<FishCaughtItem>,
                    response: Response<FishCaughtItem>
                ) {
                    if (response.isSuccessful){
                        fish.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<FishCaughtItem>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getFishDetail(): LiveData<FishCaughtItem>{
        return fish
    }

}