package com.anisanurjanah.fahrameducation.view.task

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.adapter.TaskAdapter
import com.anisanurjanah.fahrameducation.data.Task
import com.anisanurjanah.fahrameducation.databinding.ActivityTaskBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    private var client = AsyncHttpClient()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", "")

        client = AsyncHttpClient().apply {
            addHeader("Authorization", "Bearer $token")
            addHeader("Accept", "Application/Json")
        }

        setupAction()
        setupToolbar()
        getUserTask()
    }

    private fun setupAction() {
        binding.btSave.setOnClickListener {
            setupInputTask()
        }
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = getString(R.string.my_task)
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getUserTask() {
        binding.progressIndicator.visibility = View.VISIBLE

        val url = "https://fahram.dev/api/tasks"

        client.get(url, object : AsyncHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                binding.progressIndicator.visibility = View.GONE

                val response = String(responseBody)
                try {
                    val dataArray = JSONArray(response)
                    val data = ArrayList<Task>()

                    for (i in 0 until dataArray.length()) {
                        val dataObj = dataArray.getJSONObject(i)

                        val id = dataObj.getInt("id")
                        val name = dataObj.getString("name")
                        val createdAt = dataObj.getString("created_at")
                        val completed = dataObj.getInt("completed")

                        val task = Task(id, name, createdAt, completed)
                        data.add(task)
                    }

                    taskAdapter = TaskAdapter(data, { task, status ->
                        setupUpdateTask(task, status)
                    }, { task ->
                        setupDeleteTask(task)
                    })

                    binding.rvTask.layoutManager = LinearLayoutManager(this@TaskActivity)
                    binding.rvTask.adapter = taskAdapter
                    taskAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(this@TaskActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@TaskActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupInputTask() {
        binding.progressIndicator.visibility = View.VISIBLE

        val taskName = binding.edInput.text.toString()

        if (taskName.isEmpty()) {
            binding.progressIndicator.visibility = View.GONE
            Toast.makeText(this, "Task name cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "https://fahram.dev/api/task/store"

        val obj = JSONObject().apply {
            put("name", taskName)
            put("completed", 0)
        }

        val data = StringEntity(obj.toString())

        client.post(
            null,
            url,
            data,
            "Application/json",
            object : AsyncHttpResponseHandler() {
                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    binding.progressIndicator.visibility = View.GONE

                    try {
                        taskAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            this@TaskActivity,
                            "Successfully added!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@TaskActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@TaskActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setupUpdateTask(task: Task, status: Boolean) {
        binding.progressIndicator.visibility = View.VISIBLE

        val url = "https://fahram.dev/api/task/update"

        val obj = JSONObject().apply {
            put("id", task.id)
            put("name", task.name)
            put("completed", if (status) 1 else 0)
        }

        val data = StringEntity(obj.toString())

        client.post(
            null,
            url,
            data,
            "Application/json",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                    binding.progressIndicator.visibility = View.GONE

                    try {
                        taskAdapter.updateTask(task, status)

                        val message = if (status) "Successfully checked!" else "Successfully unchecked!"
                        Toast.makeText(this@TaskActivity, message, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@TaskActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@TaskActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun setupDeleteTask(task: Task) {
        binding.progressIndicator.visibility = View.VISIBLE

        val url = "https://fahram.dev/api/task/delete"

        val obj = JSONObject().apply {
            put("id", task.id)
        }

        val data = StringEntity(obj.toString())

        client.post(
            null,
            url,
            data,
            "Application/json",
            object : AsyncHttpResponseHandler() {
                @SuppressLint("NotifyDataSetChanged")
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    binding.progressIndicator.visibility = View.GONE

                    try {
                        taskAdapter.removeTask(task)
                        taskAdapter.notifyDataSetChanged()
                        Toast.makeText(
                            this@TaskActivity,
                            "Successfully deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@TaskActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@TaskActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}