package se.onemanstudio.test.umain.network

import retrofit2.http.GET
import retrofit2.http.Path
import se.onemanstudio.test.umain.network.dto.FilterSuccessResponse
import se.onemanstudio.test.umain.network.dto.OpenStatusSuccessResponse
import se.onemanstudio.test.umain.network.dto.RestaurantsResponse

interface FoodDeliveryService {
    @GET("filter/{id}")
    suspend fun getFilterDetails(@Path("id") filterId: String): FilterSuccessResponse

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantsResponse

    @GET("filter/{id}")
    suspend fun getOpenStatusForRestaurant(@Path("id") restaurantId: String): OpenStatusSuccessResponse
}
