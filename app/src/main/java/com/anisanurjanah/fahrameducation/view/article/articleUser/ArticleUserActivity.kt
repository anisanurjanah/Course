package com.anisanurjanah.fahrameducation.view.article.articleUser

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.adapter.ArticleAdapter
import com.anisanurjanah.fahrameducation.data.Article
import com.anisanurjanah.fahrameducation.databinding.ActivityArticleUserBinding
import com.anisanurjanah.fahrameducation.view.article.ArticleDetailActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import org.json.JSONObject

class ArticleUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        getAllArticles()
    }

    private fun setupToolbar() {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = getString(R.string.articles)
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun getAllArticles() {
        binding.progressIndicator.visibility = View.VISIBLE

        val sharedPref = getSharedPreferences("UNIVAL", MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", "")

        val client = AsyncHttpClient().apply {
            addHeader("Authorization", "Bearer $token")
        }

        val url = "https://fahram.dev/api/posts"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                responseBody: ByteArray
            ) {
                binding.progressIndicator.visibility = View.GONE

                val response = String(responseBody)
                try {
                    val responseObj = JSONObject(response)
                    val dataArray = responseObj.getJSONArray("data")
                    val data = ArrayList<Article>()

                    for (i in 0 until dataArray.length()) {
                        val dataObj = dataArray.getJSONObject(i)

                        val title = dataObj.getString("title")
                        val image = dataObj.getString("image")
                        val excerpt = dataObj.getString("excerpt")
                        val slug = dataObj.getString("slug")
                        val author = dataObj.getString("author")
                        val authorImage = dataObj.getString("authorimage")
                        val category = dataObj.getString("category")
                        val view = dataObj.getInt("view")
                        val publishedAt = dataObj.getString("published_at")

                        val article = Article(title, image, excerpt, slug,
                            author, authorImage, category, view, publishedAt)
                        data.add(article)
                    }

                    val adapter = ArticleAdapter(data) { article ->
                        startActivity(
                            Intent(this@ArticleUserActivity, ArticleDetailActivity::class.java
                            ).putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article))
                    }
                    binding.rvArticle.layoutManager = LinearLayoutManager(this@ArticleUserActivity)
                    binding.rvArticle.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@ArticleUserActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
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
                Toast.makeText(this@ArticleUserActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}