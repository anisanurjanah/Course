package com.anisanurjanah.fahrameducation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.anisanurjanah.fahrameducation.data.Article
import com.anisanurjanah.fahrameducation.databinding.ItemArticleBinding

class ArticleAdapter(
    private val data : ArrayList<Article>,
    val onClick : (Article) -> Unit
)
    : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = data[position]
        holder.binding.root.setOnClickListener { onClick(article) }
        holder.binding.articleImage.load(article.image)
        holder.binding.articleTitle.text = article.title
        holder.binding.articleAuthor.text = article.author
    }
}