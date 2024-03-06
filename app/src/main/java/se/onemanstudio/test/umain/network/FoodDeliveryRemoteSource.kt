package se.onemanstudio.test.umain.network

import javax.inject.Inject

class FoodDeliveryRemoteSource @Inject constructor(private val deliveryService: FoodDeliveryService) {

    suspend fun getRestaurants() = deliveryService.getRestaurants()

    suspend fun getFilterDetails(id: String) = deliveryService.getFilterDetails(id)

    suspend fun getOpenStatusForRestaurant(id: String) = deliveryService.getOpenStatusForRestaurant(id)
}
