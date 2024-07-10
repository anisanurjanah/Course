package com.anisanurjanah.fahrameducation.view.article

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.anisanurjanah.fahrameducation.R
import com.anisanurjanah.fahrameducation.data.Article
import com.anisanurjanah.fahrameducation.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        article?.let {
            setupToolbar(it.title.toString())
            setupArticleDetail(it)
        }
    }

    private fun setupToolbar(title: String) {
        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.title = title
            topAppBar.setNavigationIcon(R.drawable.ic_arrow_back)
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupArticleDetail(article: Article) {
        binding.apply {
            articleImage.load(article.image)
            articleTitle.text = article.title ?: getString(R.string.none)
            articleExcerpt.text = article.excerpt ?: getString(R.string.none)
            articleSlug.text = article.slug ?: getString(R.string.none)
            authorName.text = article.author ?: getString(R.string.none)
            authorImage.load(article.authorImage)
            articleCategory.text = article.category ?: getString(R.string.none)
            articleView.text = article.view?.toString() ?: getString(R.string.none)
            publishedAt.text = article.publishedAt ?: getString(R.string.none)
        }
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}