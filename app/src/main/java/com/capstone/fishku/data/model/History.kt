package com.capstone.fishku.data.model

import com.google.gson.annotations.SerializedName

data class History(

	@field:SerializedName("History")
	val history: List<HistoryItem?>? = null
)

data class HistoryItem(

	@field:SerializedName("id_order")
	val idOrder: Int? = null,

	@field:SerializedName("status_order")
	val statusOrder: String? = null,

	@field:SerializedName("id_history")
	val idHistory: Int? = null
)
