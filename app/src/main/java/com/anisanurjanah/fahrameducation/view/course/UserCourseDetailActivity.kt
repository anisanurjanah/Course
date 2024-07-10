package com.anisanurjanah.fahrameducation.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ActivityUserCourseDetailBinding

class UserCourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCourseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val course = intent.getParcelableExtra<Course>(EXTRA_COURSE)
        course?.let {
            setupCourseDetail(it)
        }
    }

    private fun setupCourseDetail(course: Course) {
        binding.apply {
            courseImage.load(course.featuredImage)
            courseTitle.text = course.title
            courseExcerpt.text = course.excerpt
            courseSlug.text = course.slug
            courseLevel.text = course.infoLevel
            courseView.text = course.view.toString()
            publishedAt.text = course.publishedAt
        }
    }

    companion object {
        const val EXTRA_COURSE = "extra_course"
    }
}