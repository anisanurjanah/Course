package com.anisanurjanah.fahrameducation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
	val title: String? = null,
	val image: String? = null,
	val excerpt: String? = null,
	val slug: String? = null,
	val author: String? = null,
	val authorImage: String? = null,
	val category: String? = null,
	val view: Int? = null,
	val publishedAt: String? = null
) : Parcelable
