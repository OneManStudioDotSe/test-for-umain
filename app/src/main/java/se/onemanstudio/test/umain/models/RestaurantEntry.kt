package se.onemanstudio.test.umain.models

data class RestaurantEntry(
    val title: String,
    val promoImageUrl: String,
    val tags: List<TagEntry>,
    val rating: Double,
    val openTimeAsText: String
)
