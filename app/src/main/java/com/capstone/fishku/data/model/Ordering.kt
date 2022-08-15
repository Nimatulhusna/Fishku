package com.capstone.fishku.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Ordering(

	@field:SerializedName("Ordering")
	val ordering: List<OrderingItem?>? = null
)


@Parcelize
data class OrderingItem(

	@field:SerializedName("time_order")
	val timeOrder: Int? = null,

	@field:SerializedName("id_order")
	val idOrder: Int? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("id_cart")
	val idCart: Int? = null,

	@field:SerializedName("id_fisher")
	val idFisher: Int? = null
): Parcelable
