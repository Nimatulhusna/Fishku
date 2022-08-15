package com.capstone.fishku.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.capstone.fishku.data.model.UserPreference
import com.capstone.fishku.data.retrofit.ApiConfig

class MainViewModel(private val pref: UserPreference): ViewModel() {

    val apiInterface = ApiConfig.getApiService().getProfile()


}