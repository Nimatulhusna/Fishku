package com.capstone.fishku.data.model

import com.google.gson.annotations.SerializedName

data class FishCaught(

	@field:SerializedName("FishCaught")
	val fishCaught: ArrayList<FishCaughtItem>
)


data class FishCaughtItem(

	@field:SerializedName("fish_name")
	val fishName: String? = null,

	@field:SerializedName("id_fish")
	val idFish: Int? = null,

	@field:SerializedName("id_consumer")
	val idConsumer: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("time_caught")
	val timeCaught: Int? = null,

	@field:SerializedName("id_fisher")
	val idFisher: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("location_harbor")
	val locationHarbor: String? = null,

	@field:SerializedName("desc_fish")
	val descFish: String? = null
)
