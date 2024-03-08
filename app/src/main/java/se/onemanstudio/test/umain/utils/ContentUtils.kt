package se.onemanstudio.test.umain.utils

import se.onemanstudio.test.OneManApplication
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import timber.log.Timber
import kotlin.random.Random
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ContentUtils {
    private const val TAGS_SEPARATOR = " • "

    fun createRandomDescription(): String {
        val list = listOf(
            "Tristique et egestas quis ipsum.",
            "Sociis natoque penatibus et magnis. Cras pulvinar mattis nunc sed blandit libero volutpat sed cras.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "Purus ut faucibus pulvinar elementum integer enim. Lacus sed viverra tellus in hac habitasse. Condimentum lacinia quis vel eros donec.",
            "Diam vulputate ut pharetra sit amet aliquam id. Cursus mattis molestie a iaculis at erat. Id leo in vitae turpis. Amet tellus cras adipiscing enim eu turpis egestas. Nam at lectus urna duis convallis convallis tellus. ",
            "Posuere urna nec tincidunt praesent. Ultrices dui sapien eget mi proin sed libero enim sed. Facilisis mauris sit amet massa vitae tortor condimentum lacinia. Dignissim sodales ut eu sem integer vitae justo eget. Varius duis at consectetur lorem donec massa.",
        )
        val randomIndex = Random.nextInt(list.size)

        // here we can use the selected element to print it for example
        return list[randomIndex]
    }

    fun convertDeliveryTimeToReadableForm(amount: Int): String {
        val min = amount.toDuration(DurationUnit.MINUTES)
        val context = OneManApplication.context

        min.toComponents { days, hours, minutes, _, _ ->
            // Ignore seconds and nanoseconds
            Timber.d("Delivery in $days days, $hours hours, and $minutes minutes ($amount)")

            if (days >= 1) {
                return context.getString(R.string.delivery_in_days)
            } else {
                if (hours >= 1) {
                    return String.format(context.getString(R.string.delivery_in_hours), hours)
                } else {
                    return String.format(context.getString(R.string.delivery_in_mins), minutes)
                }
            }
        }
    }

    fun convertTagsIntoSingleString(tags: List<TagEntry>): String {
        return tags.joinToString(separator = TAGS_SEPARATOR) { it.title }
    }

    fun getSampleTagsSingle(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "asd",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            )
        )
    }

    fun getSampleTagsFew(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "asd",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            ),

            TagEntry(
                id = "fgh",
                title = "Take Out",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "jkl",
                title = "Fast Delivery",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_delivery.png",
            )
        )
    }

    fun getSampleTagsMany(): List<TagEntry> {
        return listOf(
            TagEntry(
                id = "asd",
                title = "Fast Food",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            ),

            TagEntry(
                id = "fgh",
                title = "Take Out",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "jkl",
                title = "Fast Delivery",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_delivery.png",
            ),

            TagEntry(
                id = "qwe",
                title = "Top Rated",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_top_rated.png",
            ),

            TagEntry(
                id = "rty",
                title = "Take Out 2",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_take_out.png",
            ),

            TagEntry(
                id = "uio",
                title = "Fast Food 2",
                tagImageUrl = "https://food-delivery.umain.io/images/filter/filter_fast_food.png",
            )
        )
    }

    fun getSampleRestaurants(): List<RestaurantEntry> {
        return listOf(
            RestaurantEntry(
                title = "Wayne 'Chad Broski' Burgers",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/burgers.png",
                tags = getSampleTagsFew(),
                tagsInitially = listOf(),
                rating = 4.7,
                openTimeAsText = "42 mins"
            ),

            RestaurantEntry(
                title = "Emilia's Fancy Foo",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/meat.png",
                tags = getSampleTagsSingle(),
                tagsInitially = listOf(),
                rating = 4.1,
                openTimeAsText = "1 min"
            ),

            RestaurantEntry(
                title = "Pizzeria Varsha",
                promoImageUrl = "https://food-delivery.umain.io/images/restaurant/pizza.png",
                tags = getSampleTagsMany(),
                tagsInitially = listOf(),
                rating = 5.0,
                openTimeAsText = "1 hour"
            ),

            RestaurantEntry(
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