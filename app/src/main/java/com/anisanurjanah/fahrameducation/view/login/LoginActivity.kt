package com.anisanurjanah.fahrameducation.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.databinding.ActivityLoginBinding
import com.anisanurjanah.fahrameducation.view.profile.ProfileActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupImage()
    }

    private fun setupAction() {
        binding.buttonLogin.setOnClickListener {
            setupLogin()
        }
    }

    private fun setupImage() {
        val image = getString(R.string.logo)
        binding.imageLogo.load(image)
    }

    private fun setupLogin() {
        binding.progressBar.visibility = View.VISIBLE

        val sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", "")

        if (token != "") {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        val email = binding.emailInputEditText.text
        val password = binding.passwordInputEditText.text

        val client = AsyncHttpClient().apply {
            addHeader("Accept", "Application/Json")
        }

        val url = "https://fahram.dev/api/login"

        val obj = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val data = StringEntity(obj.toString())

        client.post(
            null,
            url,
            data,
            "Application/json",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    binding.progressBar.visibility = View.GONE

                    val response = String(responseBody)
                    try {
                        sharedPref.edit()
                            .putString("TOKEN", response)
                            .apply()
                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}