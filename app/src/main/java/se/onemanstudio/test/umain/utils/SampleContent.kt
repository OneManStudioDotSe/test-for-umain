package se.onemanstudio.test.umain.utils

import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry

object SampleContent {
    private fun getSampleTagsSingle(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "id1",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            )
        )
    }

    fun getSampleTagsFew(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "id2",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            ),

            TagEntry(
                id = "id3",
                title = "Take Out",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "id4",
                title = "Fast Delivery",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_delivery.png",
            )
        )
    }

    fun getSampleTagsMany(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "id1",
                title = "Fast Food",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            ),

            TagEntry(
                id = "id2",
                title = "Take Out",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "id3",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_top_rated.png",
            ),

            TagEntry(
                id = "id4",
                title = "Fast Delivery",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_delivery.png",
            ),

            TagEntry(
                id = "id5",
                title = "Take Out 2",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "id6",
                title = "Fast Food 2",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            )
        )
    }

    fun getSampleRestaurants(): List<RestaurantEntry> {
        return listOf(
            RestaurantEntry(
                id = "id1",
                title = "Wayne 'Chad Broski' Burgers",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/burgers.png",
                tags = getSampleTagsFew(),
                tagsInitially = listOf(),
                rating = 4.7,
                openTimeAsText = "42 mins"
            ),

            RestaurantEntry(
                id = "id2",
                title = "Emilia's Fancy Foo",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/meat.png",
                tags = getSampleTagsSingle(),
                tagsInitially = listOf(),
                rating = 4.1,
                openTimeAsText = "1 min"
            ),

            RestaurantEntry(
                id = "id3",
                title = "Pizzeria Varsha",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/pizza.png",
                tags = getSampleTagsMany(),
                tagsInitially = listOf(),
                rating = 5.0,
                openTimeAsText = "1 hour"
            ),

            RestaurantEntry(
                id = "id4",
                title = "Henriks Muddy Water",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/coffee.png",
                tags = getSampleTagsFew(),
                tagsInitially = listOf(),
                rating = 0.2,
                openTimeAsText = "4 mins"
            )
        )
    }
}
