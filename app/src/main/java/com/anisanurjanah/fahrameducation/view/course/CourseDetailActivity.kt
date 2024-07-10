package com.anisanurjanah.fahrameducation.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ActivityCourseDetailBinding

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val course = intent.getParcelableExtra<Course>(EXTRA_COURSE)
        course?.let {
            setupCourseDetail(it)
        }
    }

    private fun setupCourseDetail(course: Course) {
        binding.apply {
            courseImage.load(course.image)
            courseTitle.text = course.title
            coursePath.text = course.path
            courseExcerpt.text = course.excerpt
            courseSlug.text = course.slug
            teacherName.text = course.teacher
            teacherImage.load(course.teacherImage)
            courseLevel.text = course.level
            courseView.text = course.view.toString()
            courseModule.text = course.module.toString()
            publishedAt.text = course.publishedAt
        }
    }

    companion object {
        const val EXTRA_COURSE = "extra_course"
    }
}