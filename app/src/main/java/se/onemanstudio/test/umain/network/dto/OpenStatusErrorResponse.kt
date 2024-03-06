package se.onemanstudio.test.umain.network.dto

import com.google.gson.annotations.SerializedName

data class OpenStatusErrorResponse(
    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("reason") var reason: String? = null
)
