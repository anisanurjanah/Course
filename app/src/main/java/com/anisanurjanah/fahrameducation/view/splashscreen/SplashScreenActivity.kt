package com.anisanurjanah.fahrameducation.view.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.databinding.ActivitySplashScreenBinding
import com.anisanurjanah.fahrameducation.view.onboarding.OnboardingActivity
import com.anisanurjanah.fahrameducation.view.profile.ProfileActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private var delay: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupImage()
        moveToMain()
    }

    private fun setupImage() {
        val image = getString(R.string.logo)
        binding.imageLogo.load(image)
    }

    private fun moveToMain() {
        window.decorView.postDelayed({
            val sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
            val token = sharedPref.getString("TOKEN", "")

            if (token != "") {
                startActivity(Intent(this@SplashScreenActivity, ProfileActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreenActivity, OnboardingActivity::class.java))
            }
        }, delay)
    }
}