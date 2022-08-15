package com.capstone.fishku.data.model

import com.google.gson.annotations.SerializedName

data class Register(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("email")
	val email: String? = null


//	@field:SerializedName("id")
//	val id: Int? = null,

)
