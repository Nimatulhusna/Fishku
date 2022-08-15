package com.capstone.fishku.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Cart(

	@field:SerializedName("Cart")
	val cart: List<CartItem?>? = null
)

@Parcelize
data class CartItem(

	@field:SerializedName("id_fish")
	val idFish: Int? = null,

	@field:SerializedName("id_consumer")
	val idConsumer: Int? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("total_price")
	val totalPrice: Int? = null,

	@field:SerializedName("qty")
	val qty: Int? = null,

	@field:SerializedName("expedition_service")
	val expeditionService: String? = null,

	@field:SerializedName("id_cart")
	val idCart: Int? = null
): Parcelable
