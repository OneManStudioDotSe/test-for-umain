package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("filterIds") var filterIds: ArrayList<String> = arrayListOf(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("delivery_time_minutes") var deliveryTimeMinutes: Int? = null,
    @SerializedName("name") var name: String? = null
)