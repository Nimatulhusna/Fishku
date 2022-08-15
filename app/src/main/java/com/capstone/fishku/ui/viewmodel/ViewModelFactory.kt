package com.capstone.fishku.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.fishku.data.model.UserPreference

class ViewModelFactory (private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory(){

    private lateinit var mApplication: Application

    fun setApplication(application: Application){
        mApplication = application
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)){
            return AuthenticationViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            return MarketViewModel() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}