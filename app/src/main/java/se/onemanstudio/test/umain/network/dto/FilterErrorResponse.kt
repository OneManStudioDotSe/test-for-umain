package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class FilterErrorResponse(
    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("reason") var reason: String? = null
)
