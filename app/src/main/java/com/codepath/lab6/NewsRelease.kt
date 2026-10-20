package com.codepath.lab6

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NewsResponse(
    @SerialName("data")
    val data: List<NewsRelease>?
)

// One news release from the NPS newsreleases endpoint
@Keep
@Serializable
data class NewsRelease(
    @SerialName("title")
    val title: String?,
    @SerialName("abstract")
    val abstract: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("image")
    val image: NewsImage?
)

@Keep
@Serializable
data class NewsImage(
    @SerialName("url")
    val url: String?
)
