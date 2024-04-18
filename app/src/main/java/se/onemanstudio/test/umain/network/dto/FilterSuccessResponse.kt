package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName

data class FilterSuccessResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("name") var name: String? = null
)
