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
import com.anisanurjanah.fahrameducation.view.article.ArticleActivity
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

        binding.articleButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ArticleActivity::class.java))
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

                    val photo = responseObj.getString("profile_photo_url")
                    val name = responseObj.getString("name")
                    val role = responseObj.getString("role")
                    val nim = responseObj.getString("nim")
                    val clas = responseObj.getString("kelas")
                    val campus = responseObj.getString("kampus")
                    val phone = responseObj.getString("phone")
                    val email = responseObj.getString("email")
                    val joinedAt = responseObj.getString("joined_at")

                    binding.apply {
                        ivUser.load(photo)
                        userName.text = if (!name.isNullOrEmpty()) name else getString(R.string.none)
                        userRole.text = if (!role.isNullOrEmpty()) role else getString(R.string.none)
                        userNim.text = if (!nim.isNullOrEmpty()) nim else getString(R.string.none)
                        userClass.text = if (!clas.isNullOrEmpty()) clas else getString(R.string.none)
                        userCampus.text = if (!campus.isNullOrEmpty()) campus else getString(R.string.none)
                        userPhone.text = if (!phone.isNullOrEmpty()) phone else getString(R.string.none)
                        userEmail.text = if (!email.isNullOrEmpty()) email else getString(R.string.none)
                        userJoined.text = if (!joinedAt.isNullOrEmpty()) joinedAt else getString(R.string.none)
                    }
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
                Toast.makeText(this@ProfileActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}