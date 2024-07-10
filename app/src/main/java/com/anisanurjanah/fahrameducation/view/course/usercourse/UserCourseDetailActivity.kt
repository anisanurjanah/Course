package com.anisanurjanah.fahrameducation.view.course.usercourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ActivityUserCourseDetailBinding
import com.anisanurjanah.fahrameducation.utils.withDateFormat

class UserCourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCourseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val course = intent.getParcelableExtra<Course>(EXTRA_COURSE)
        course?.let {
            setupToolbar(it.title)
            setupCourseDetail(it)
        }
    }

    private fun setupToolbar(title: String) {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = title
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupCourseDetail(course: Course) {
        binding.apply {
            courseImage.load(course.image)
            courseTitle.text = course.title
            courseExcerpt.text = course.excerpt
            courseSlug.text = course.slug
            courseLevel.text = course.level
            courseView.text = course.view.toString()
            publishedAt.text = course.publishedAt.withDateFormat()
        }
    }

    companion object {
        const val EXTRA_COURSE = "extra_course"
    }
}