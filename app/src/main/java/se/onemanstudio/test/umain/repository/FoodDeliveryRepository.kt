package se.onemanstudio.test.umain.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import se.onemanstudio.test.umain.network.FoodDeliveryRemoteSource
import se.onemanstudio.test.umain.network.dto.FilterSuccessResponse
import se.onemanstudio.test.umain.network.dto.RestaurantsResponse
import javax.inject.Inject

@ActivityRetainedScoped
class FoodDeliveryRepository @Inject constructor(private val deliveryRemoteSource: FoodDeliveryRemoteSource) {

    suspend fun getRestaurants(): RestaurantsResponse {
        return deliveryRemoteSource.getRestaurants()
    }

    suspend fun getFilterDetails(id: String): FilterSuccessResponse {
        return deliveryRemoteSource.getFilterDetails(id)
    }

    suspend fun getOpenStatusForRestaurant(id: String) {
        deliveryRemoteSource.getOpenStatusForRestaurant(id)
    }
}
