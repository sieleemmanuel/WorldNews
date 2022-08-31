package com.siele.worldnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.siele.worldnews.R
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.databinding.FragmentNewsDetailBinding
import com.siele.worldnews.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetail : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val args:NewsDetailArgs by navArgs()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        article = args.article!!
        binding = FragmentNewsDetailBinding.inflate(inflater)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)

        binding.apply {
            val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_progressbar)
                .error(R.drawable.image_placeholder)
                .priority(Priority.HIGH)

            Glide.with(requireContext()).load(article.urlToImage!!).apply(options).into(imageViewArticle)
            setUpToolbar(appBarConfiguration)
            webView.apply {
                webViewClient = ArticleWebViewClient()
                loadUrl(article.url)
            }
        }
        return binding.root
    }

    private fun FragmentNewsDetailBinding.setUpToolbar(
        appBarConfiguration: AppBarConfiguration
    ) {
        newsDetailToolbar.apply {
            setupWithNavController(findNavController(), appBarConfiguration)
            title = "2 hours ago"

            setOnMenuItemClickListener {  menuItem ->
                 when(menuItem.itemId){
                    R.id.actionBookmark ->{
                        mainViewModel.bookmarkAnArticle(article = article)
                        true
                    }
                     R.id.actionShare ->{
                         shareArticle()
                         true
                     }
                    else ->false
                }

            }
        }
    }

    private fun shareArticle() {
        TODO("Not yet implemented")
    }

    inner class ArticleWebViewClient:WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.pbLoadingArticle.visibility = View.INVISIBLE
        }
    }

}