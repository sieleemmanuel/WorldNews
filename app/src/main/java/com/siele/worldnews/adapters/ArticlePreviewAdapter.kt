package com.siele.worldnews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siele.worldnews.databinding.PreviewArticleItemBinding
import com.siele.worldnews.model.Article

class ArticlePreviewAdapter(val articleClickListener: ArticleClickListener,val context: Context) :ListAdapter<Article,ArticlePreviewAdapter.ArticlePreviewViewHolder>(DiffUtilItem){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePreviewViewHolder {
        val binding = PreviewArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticlePreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlePreviewViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticlePreviewViewHolder(private val binding: PreviewArticleItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article){
            binding.apply {
                tvNewsCategory.text ="General"
                tvArticleTitle.text = article.title
                tvEllapsedTime.text = article.publishedAt
                Glide.with(context).load(article.urlToImage).centerCrop().into(imgArticlePreview)

                root.setOnClickListener {
                    articleClickListener.onArticleClicked(article)
                }
            }
        }
    }

    object DiffUtilItem:DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    class ArticleClickListener(val articleListener:(article:Article)->Unit){
        fun onArticleClicked(article: Article) = articleListener(article)
    }


}
