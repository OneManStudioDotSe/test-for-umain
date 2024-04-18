package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName

data class RestaurantsResponse(
    @SerializedName("restaurants") var restaurants: ArrayList<Restaurant> = arrayListOf()
)
