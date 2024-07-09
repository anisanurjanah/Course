package com.anisanurjanah.fahrameducation.view.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anisanurjanah.fahrameducation.databinding.ActivityCourseDetailBinding

class CourseDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}