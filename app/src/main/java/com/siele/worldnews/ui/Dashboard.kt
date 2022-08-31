package com.siele.worldnews.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.siele.worldnews.R
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.adapters.NewsPagerAdapter
import com.siele.worldnews.adapters.NewsTopHeadlinesAdapter
import com.siele.worldnews.databinding.FragmentDashboardBinding
import com.siele.worldnews.utils.Constants
import com.siele.worldnews.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var newsPagerAdapter:NewsPagerAdapter
    private lateinit var articlePreviewAdapter: ArticlePreviewAdapter
    private val mainViewModel:MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentDashboardBinding.inflate(inflater)
        newsPagerAdapter = NewsPagerAdapter(childFragmentManager,lifecycle,requireContext())
        articlePreviewAdapter = ArticlePreviewAdapter(articleClickListener(), viewClickListener(),bookmarkListener(),requireContext())
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.apply {
            rvLatestNews.apply {
                adapter = articlePreviewAdapter
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            }
            dashboardToolbar.setupWithNavController(findNavController(),appBarConfiguration)
            vpNewsHeadline.adapter = newsPagerAdapter
            TabLayoutMediator(tabLayout, vpNewsHeadline) { tab, position ->
                val tabsTitles = arrayListOf(
                    getString(R.string.all_news_label),
                    resources.getString(R.string.business_label),
                    resources.getString(R.string.entertainment_label),
                    resources.getString(R.string.health_label),
                    resources.getString(R.string.sports_label),
                    resources.getString(R.string.science_label),
                    resources.getString(R.string.tech_label)
                )
                tab.text = tabsTitles[position]
            }.attach()
        }
        mainViewModel.topHeadline.observe(viewLifecycleOwner){
            if (it!=null){
                articlePreviewAdapter.submitList(null)
                articlePreviewAdapter.submitList(it.articles)
                binding.pbLoadingLatestNews.visibility = View.INVISIBLE
            }

        }
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Suppress("DEPRECATION")
    private fun viewClickListener()= ArticlePreviewAdapter.ViewClickListener { imgBtn, article ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imgBtn.imageTintList = resources.getColorStateList(R.color.purple_200, null)
        }else{
            imgBtn.imageTintList = resources.getColorStateList(R.color.purple_200)
        }
        mainViewModel.bookmarkAnArticle(article)
    }

    private fun articleClickListener() = ArticlePreviewAdapter.ArticleClickListener { article ->
        findNavController().navigate(DashboardDirections.actionDashboardToNewsDetail(article))
    }
    private fun bookmarkListener() = ArticlePreviewAdapter.BookmarkListener{ binding, article ->
        mainViewModel.isArticleInBookmark(article).observe(viewLifecycleOwner){
            if (it){
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmarked)
            }else{
                binding.btnBookmark.setImageResource(R.drawable.ic_bookmark)
            }
        }

    }
}