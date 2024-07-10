package com.anisanurjanah.fahrameducation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
	val id: String? = null,
	val title: String? = null,
	val infoLevel: String? = null,
	val featuredImage: String? = null,
	val excerpt: String? = null,
	val publishedAt: String? = null,
	val slug: String? = null,
	val view: Int? = null
) : Parcelable
