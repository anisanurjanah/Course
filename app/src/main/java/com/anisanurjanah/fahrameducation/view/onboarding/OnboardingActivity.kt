package com.anisanurjanah.fahrameducation.view.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.databinding.ActivityOnboardingBinding
import com.anisanurjanah.fahrameducation.view.course.CourseActivity
import com.anisanurjanah.fahrameducation.view.login.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupImage()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, LoginActivity::class.java))
        }

        binding.coursesButton.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, CourseActivity::class.java))
        }
    }

    private fun setupImage() {
        val image = getString(R.string.logo)
        binding.imageLogo.load(image)
    }
}