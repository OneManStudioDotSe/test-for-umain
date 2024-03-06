package se.onemanstudio.test.umain.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import se.onemanstudio.test.umain.network.FoodDeliveryRemoteSource
import javax.inject.Inject

@ActivityRetainedScoped
class FoodDeliveryRepository @Inject constructor(private val deliveryRemoteSource: FoodDeliveryRemoteSource) {

    suspend fun getRestaurants() {
        deliveryRemoteSource.getRestaurants()
    }

    suspend fun getFilterDetails(id: String) {
        deliveryRemoteSource.getFilterDetails(id)
    }

    suspend fun getOpenStatusForRestaurant(id: String) {
        deliveryRemoteSource.getOpenStatusForRestaurant(id)
    }
}
