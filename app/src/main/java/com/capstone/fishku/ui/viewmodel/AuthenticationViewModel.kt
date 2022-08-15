package com.capstone.fishku.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.capstone.fishku.data.model.*
import com.capstone.fishku.helper.Resource
import com.capstone.fishku.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationViewModel(private val pref: UserPreference) : ViewModel(){
    private val _infoAuth = MutableLiveData<Resource<String>>()
    val infoAuth: LiveData<Resource<String>> = _infoAuth

    fun login(email: String?, password: String?){
        _infoAuth.postValue(Resource.Loading())
        val client = ApiConfig.getApiService().login(UserLogin(email.toString(),
            password.toString()
        ))

        client.enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful){
                    val loginResult = response.body()?.user?.email

                    loginResult?.let { saveUser(it) }
                    _infoAuth.postValue(Resource.Success(loginResult))
                }else{
                    _infoAuth.postValue(Resource.Error("Error"))
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Log.e(
                    AuthenticationViewModel::class.java.simpleName,
                    "onFailure login"
                )
                _infoAuth.postValue(Resource.Error(t.message))
            }

        })
    }

    fun register(
        name: String?,
        email: String?,
        password: String?,
        phone_number: String?,
        address: String?
    ){
        _infoAuth.postValue(Resource.Loading())
        val client = ApiConfig.getApiService().register(
            User(name.toString(), address.toString(), password.toString(), phone_number.toString(), email.toString())
        )

        client.enqueue(object : Callback<Register>{
            override fun onResponse(call: Call<Register>, response: Response<Register>) {
                if (response.isSuccessful){
                    val message = response.body()?.user.toString()
                    _infoAuth.postValue(Resource.Success(message))
                }else{
                    _infoAuth.postValue(Resource.Error("Error"))
                }
            }

            override fun onFailure(call: Call<Register>, t: Throwable) {
                Log.e(
                    AuthenticationViewModel::class.java.simpleName,
                    "onFailure register"
                )
                _infoAuth.postValue(Resource.Error(t.message))
            }

        })
    }

    fun logout() = deleteUser()

    fun getUser() = pref.getUser().asLiveData()

    private fun saveUser(key: String){
        viewModelScope.launch { pref.saveUser(key) }
    }

    private fun deleteUser(){
        viewModelScope.launch { pref.deleteUser() }
    }
}