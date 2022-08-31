package com.siele.worldnews.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.siele.worldnews.R
import com.siele.worldnews.databinding.TopHeadlineItemBinding
import com.siele.worldnews.data.model.Article

class NewsTopHeadlinesAdapter(
    val headlineClickListener: HeadlineClickListener,
    val viewListener: ViewClickListener,
    val context: Context
    ):ListAdapter<Article, NewsTopHeadlinesAdapter.HeadlineViewHolder>(DiffUtilItem) {
    private val TAG = NewsTopHeadlinesAdapter::class.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val binding = TopHeadlineItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val topArticle= getItem(position)
        holder.bind(topArticle)
    }

    inner class HeadlineViewHolder(private val binding: TopHeadlineItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun bind(topArticle: Article){
            binding.apply {
                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.loading_progressbar)
                    .error(R.drawable.image_placeholder)
                    .priority(Priority.HIGH)

                Glide.with(context).load(topArticle.urlToImage).apply(options).into(imgHeadline)
                tvHeadline.text = topArticle.title
                tvHeadlineDate.text = topArticle.publishedAt
                btnHeadlineBookmark.setOnClickListener {
                    viewListener.onHeadlineViewClicked(topArticle, it)
                }
                root.setOnClickListener {
                    Log.d(TAG, "bind: $topArticle")
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

    class ViewClickListener(val viewListener:(article:Article, view:View)->Unit){
        fun onHeadlineViewClicked(article: Article, view: View) = viewListener(article, view)
    }

}
