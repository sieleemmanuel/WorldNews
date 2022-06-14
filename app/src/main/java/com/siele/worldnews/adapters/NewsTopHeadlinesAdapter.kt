package com.siele.worldnews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siele.worldnews.databinding.TopHeadlineItemBinding
import com.siele.worldnews.model.Article

class NewsTopHeadlinesAdapter(val headlineClickListener: HeadlineClickListener,val context: Context):ListAdapter<Article, NewsTopHeadlinesAdapter.HeadlineViewHolder>(DiffUtilItem) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val binding = TopHeadlineItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val topArticle= getItem(position)
        holder.bind(topArticle)
    }

    inner class HeadlineViewHolder(private val binding: TopHeadlineItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topArticle: Article){
            binding.apply {
                Glide.with(context).load(topArticle.urlToImage).into(imgHeadline)
                tvHeadline.text = topArticle.title
                tvHeadlineDate.text = topArticle.publishedAt
                root.setOnClickListener {
                    headlineClickListener.onHeadlineClicked(topArticle)
                }
            }
        }
    }
    object DiffUtilItem:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    class HeadlineClickListener(val headlineListener:(article:Article)->Unit){
        fun onHeadlineClicked(article: Article) = headlineListener(article)
    }

}
