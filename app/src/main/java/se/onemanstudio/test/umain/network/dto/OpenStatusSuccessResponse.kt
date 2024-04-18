package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName

data class OpenStatusSuccessResponse(
    @SerializedName("is_currently_open") var isCurrentlyOpen: Boolean? = null,
    @SerializedName("restaurant_id") var restaurantId: String? = null
)
