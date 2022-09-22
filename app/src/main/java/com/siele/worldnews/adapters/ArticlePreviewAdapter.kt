package com.siele.worldnews.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.siele.worldnews.R
import com.siele.worldnews.databinding.PreviewArticleItemBinding
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.utils.GetElapsedTime
import com.siele.worldnews.viewmodels.MainViewModel

class ArticlePreviewAdapter(
    val articleClickListener: ArticleClickListener,
    val viewClickListener: ViewClickListener,
    val context: Context) :ListAdapter<Article,ArticlePreviewAdapter.ArticlePreviewViewHolder>(DiffUtilItem){

    private val TAG = ArticlePreviewAdapter::class.simpleName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlePreviewViewHolder {
        val binding = PreviewArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticlePreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticlePreviewViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticlePreviewViewHolder(private val binding: PreviewArticleItemBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForColorStateLists")
        @Suppress("DEPRECATION")
        fun bind(article: Article){
            binding.apply {
                val elapsedSecs = GetElapsedTime.getElapsedTime(article)
                tvArticleTitle.text = article.title
                 article.publishedAt
                tvEllapsedTime.text =if ((elapsedSecs/3600)>=1){
                    root.context.getString(R.string.hours_format,(elapsedSecs/3600).toInt())
                }else if((elapsedSecs/60)>=1){
                    root.context.getString(R.string.hours_format,(elapsedSecs/60).toInt())
                }else{
                    root.context.getString(R.string.about_a_minute)
                }
                btnBookmark.apply {
                if (article.isBookmarked==true) {
                    setImageResource(R.drawable.ic_bookmarked)
                    imageTintList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        root.context.resources.getColorStateList(R.color.purple_200, null)
                    } else {
                        root.context.resources.getColorStateList(R.color.purple_200)
                    }
                    }else{
                    setImageResource(R.drawable.ic_bookmark)
                    }
                }


                val options = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.loading_progressbar)
                    .error(R.drawable.image_placeholder)
                    .priority(Priority.HIGH)

                Glide.with(context).load(article.urlToImage).centerCrop().apply(options).into(imgArticlePreview)
                btnBookmark.apply {
                    setOnClickListener {
                        Log.d(TAG, "bind: $article")
                        viewClickListener.onViewClicked(btnBookmark,article)
                    }
                }
                root.setOnClickListener {
                    Log.d(TAG, "bind: $article")
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
    class ViewClickListener(val viewListener:(view:ImageButton, article:Article)->Unit){
        fun onViewClicked(view: ImageButton, article: Article) = viewListener(view, article)
    }
    class BookmarkListener(val bookmarkListener:(binding: PreviewArticleItemBinding, article:Article)->Unit){
        fun isBookmarked(binding: PreviewArticleItemBinding, article: Article) = bookmarkListener(binding, article)
    }

}
