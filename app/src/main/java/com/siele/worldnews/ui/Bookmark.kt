package com.siele.worldnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.databinding.FragmentBookmarkBinding
import com.siele.worldnews.model.Article
import com.siele.worldnews.utils.Constants.Companion.dummyNews

class Bookmark : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var newsArticleAdapter: ArticlePreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater)
        newsArticleAdapter = ArticlePreviewAdapter(articleClickListener(),requireContext())
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.apply {
            rvBookmark.adapter = newsArticleAdapter
            bookmarkToolbar.setupWithNavController(findNavController(),appBarConfiguration)
        }

        newsArticleAdapter.submitList(dummyNews)
        return binding.root
    }
    private fun articleClickListener() = ArticlePreviewAdapter.ArticleClickListener { article ->
        findNavController().navigate(BookmarkDirections.actionBookmarkToNewsDetail(article))
    }
}