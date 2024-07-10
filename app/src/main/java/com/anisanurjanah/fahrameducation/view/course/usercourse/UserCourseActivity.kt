package com.anisanurjanah.fahrameducation.view.course.usercourse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.adapter.CourseAdapter
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ActivityUserCourseBinding
import com.anisanurjanah.fahrameducation.view.course.CourseActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class UserCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupAction()
        getUserCourse()
    }

    private fun setupAction() {
        binding.coursesButton.setOnClickListener {
            startActivity(Intent(this@UserCourseActivity, CourseActivity::class.java))
        }
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = getString(R.string.my_course)
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getUserCourse() {
        binding.progressIndicator.visibility = View.VISIBLE

        val sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", "")

        val client = AsyncHttpClient().apply {
            addHeader("Authorization", "Bearer $token")
        }

        val url = "https://fahram.dev/api/course"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.progressIndicator.visibility = View.GONE

                val response = String(responseBody)
                try {
                    val dataArray = JSONArray(response)
                    val data = ArrayList<Course>()

                    for (i in 0 until dataArray.length()) {
                        val dataObj = dataArray.getJSONObject(i)

                        val title = dataObj.getString("title")
                        val image = dataObj.getString("featured_image")
                        val excerpt = dataObj.getString("excerpt")
                        val slug = dataObj.getString("slug")
                        val level = dataObj.getString("info_level")
                        val view = dataObj.getInt("view")
                        val publishedAt = dataObj.getString("published_at")

                        val course = Course(title, image, "", excerpt, slug,
                            "", "", level, view, 0, publishedAt)
                        data.add(course)
                    }

                    val adapter = CourseAdapter(data) { course ->
                        startActivity(
                            Intent(this@UserCourseActivity, UserCourseDetailActivity::class.java
                            ).putExtra(UserCourseDetailActivity.EXTRA_COURSE, course))
                    }
                    binding.rvCourse.layoutManager = LinearLayoutManager(this@UserCourseActivity)
                    binding.rvCourse.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@UserCourseActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                binding.progressIndicator.visibility = View.GONE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@UserCourseActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}