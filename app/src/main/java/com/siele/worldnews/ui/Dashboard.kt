package com.siele.worldnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var newsPagerAdapter:NewsPagerAdapter
    private lateinit var articlePreviewAdapter: ArticlePreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentDashboardBinding.inflate(inflater)

        newsPagerAdapter = NewsPagerAdapter(childFragmentManager,lifecycle)
        articlePreviewAdapter = ArticlePreviewAdapter(articleClickListener(),requireContext())
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
                    resources.getString(R.string.all_news_label),
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
        articlePreviewAdapter.submitList(Constants.dummyNews)
        return binding.root
    }

    private fun articleClickListener() = ArticlePreviewAdapter.ArticleClickListener { article ->
        findNavController().navigate(DashboardDirections.actionDashboardToNewsDetail(article))
    }
}