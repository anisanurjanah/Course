package com.anisanurjanah.fahrameducation.view.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.databinding.ActivityProfileBinding
import com.anisanurjanah.fahrameducation.view.course.usercourse.UserCourseActivity
import com.anisanurjanah.fahrameducation.view.login.LoginActivity
import com.anisanurjanah.fahrameducation.view.task.TaskActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupToolbar()
        getUserProfile()
    }

    private fun setupAction() {
        binding.courseButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, UserCourseActivity::class.java))
        }

        binding.taskButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, TaskActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            val editor = sharedPref.edit()
            editor.putString("TOKEN", "")
            editor.apply()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = getString(R.string.my_profile)
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getUserProfile() {
        binding.progressBar.visibility = View.VISIBLE

        sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", "")

        val client = AsyncHttpClient().apply {
            addHeader("Authorization", "Bearer $token")
        }

        val url = "https://fahram.dev/api/user"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.progressBar.visibility = View.GONE

                val response = String(responseBody)
                try {
                    val responseObj = JSONObject(response)

                    val name = responseObj.getString("name")
                    val photo = responseObj.getString("profile_photo_url")

                    binding.userName.text = name.toString()
                    binding.ivUser.load(photo)
                } catch (e: Exception) {
                    Toast.makeText(this@ProfileActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                binding.progressBar.visibility = View.GONE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                binding.userName.text = errorMessage
                binding.ivUser.load(R.drawable.ic_image)
            }
        })
    }
}