package se.onemanstudio.test.umain.utils

import se.onemanstudio.test.umain.models.TagEntry

object ListExtensions {
    private const val TAGS_SEPARATOR = " • "

    fun List<TagEntry>.convertTagsIntoSingleString(): String {
        return this.joinToString(separator = TAGS_SEPARATOR) { it.title }
    }
}