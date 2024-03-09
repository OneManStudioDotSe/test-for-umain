package se.onemanstudio.test.umain.repository

import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import se.onemanstudio.test.umain.network.FoodDeliveryService
import se.onemanstudio.test.umain.network.dto.FilterSuccessResponse
import se.onemanstudio.test.umain.network.dto.OpenStatusSuccessResponse
import se.onemanstudio.test.umain.network.dto.RestaurantsResponse
import javax.inject.Inject

@ActivityRetainedScoped
class FoodDeliveryRepository @Inject constructor(private val deliveryService: FoodDeliveryService) {

    suspend fun getRestaurants(): ApiResponse<RestaurantsResponse> {
        return deliveryService.getRestaurants()
    }

    suspend fun getFilterDetails(id: String): ApiResponse<FilterSuccessResponse> {
        return deliveryService.getFilterDetails(id)
    }

    suspend fun getOpenStatusForRestaurant(id: String): ApiResponse<OpenStatusSuccessResponse> {
        return deliveryService.getOpenStatusForRestaurant(id)
    }
}
