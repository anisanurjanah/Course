package com.anisanurjanah.fahrameducation.view.course

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.adapter.CourseAdapter
import com.anisanurjanah.fahrameducation.data.Course
import com.anisanurjanah.fahrameducation.databinding.ActivityCourseBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class CourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        getAllCourses()
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = getString(R.string.fahram_courses)
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getAllCourses() {
        binding.progressIndicator.visibility = View.VISIBLE

        val client = AsyncHttpClient()

        val url = "https://fahram.dev/api/v2/courses"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.progressIndicator.visibility = View.GONE

                val response = String(responseBody)
                try {
                    val responseObj = JSONObject(response)
                    val dataArray = responseObj.getJSONArray("data")
                    val data = ArrayList<Course>()

                    for (i in 0 until dataArray.length()) {
                        val dataObj = dataArray.getJSONObject(i)

                        val title = dataObj.getString("title")
                        val image = dataObj.getString("image")
                        val path = dataObj.getString("path")
                        val excerpt = dataObj.getString("excerpt")
                        val slug = dataObj.getString("slug")
                        val teacher = dataObj.getString("teacher")
                        val teacherImage = dataObj.getString("teacherimage")
                        val level = dataObj.getString("level")
                        val view = dataObj.getInt("view")
                        val module = dataObj.getInt("module")
                        val publishedAt = dataObj.getString("published_at")

                        val course = Course(title, image, path,
                            excerpt, slug, teacher, teacherImage,
                            level, view, module, publishedAt)
                        data.add(course)
                    }

                    val adapter = CourseAdapter(data) { course ->
                        startActivity(
                            Intent(this@CourseActivity, CourseDetailActivity::class.java
                            ).putExtra(CourseDetailActivity.EXTRA_COURSE, course))
                    }
                    binding.rvCourse.layoutManager = LinearLayoutManager(this@CourseActivity)
                    binding.rvCourse.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@CourseActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@CourseActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}