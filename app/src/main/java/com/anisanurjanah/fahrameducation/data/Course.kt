package com.anisanurjanah.fahrameducation.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
	val title: String,
	val image: String,
	val path: String,
	val excerpt: String,
	val slug: String,
	val teacher: String,
	val teacherImage: String,
	val level: String,
	val view: Int,
	val module: Int,
	val publishedAt: String
) : Parcelable
