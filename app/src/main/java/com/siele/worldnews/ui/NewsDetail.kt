package com.siele.worldnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.siele.worldnews.databinding.FragmentNewsDetailBinding

class NewsDetail : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private val args:NewsDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val article = args.article
        binding = FragmentNewsDetailBinding.inflate(inflater)
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)

        binding.apply {
            Glide.with(requireContext()).load(article.urlToImage).into(imageViewArticle)
            /*tvNewsTitle.text = article.title
            tvNewsContent.text = article.content*/
            newsDetailToolbar.setupWithNavController(findNavController(),appBarConfiguration)
            newsDetailToolbar.title ="2 hours ago"
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        }
        return binding.root
    }

}