package com.anisanurjanah.fahrameducation.view.article

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.adapter.ArticleAdapter
import com.anisanurjanah.fahrameducation.data.Article
import com.anisanurjanah.fahrameducation.databinding.ActivityArticleBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
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

        val client = AsyncHttpClient()

        val url = "https://fahram.dev/api/v2/posts"

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
                            Intent(this@ArticleActivity, ArticleDetailActivity::class.java
                            ).putExtra(ArticleDetailActivity.EXTRA_ARTICLE, article))
                    }
                    binding.rvArticle.layoutManager = LinearLayoutManager(this@ArticleActivity)
                    binding.rvArticle.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this@ArticleActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@ArticleActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}