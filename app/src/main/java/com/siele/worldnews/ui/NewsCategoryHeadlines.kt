package com.siele.worldnews.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.siele.worldnews.R
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.adapters.NewsTopHeadlinesAdapter
import com.siele.worldnews.databinding.FragmentNewsCategoryHeadlinesBinding
import com.siele.worldnews.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsCategoryHeadlines : Fragment() {
    private lateinit var binding: FragmentNewsCategoryHeadlinesBinding
    private lateinit var headlinesAdapter: NewsTopHeadlinesAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private var allNews: String? = null
    private var business: String? = null
    private var entertainment: String? = null
    private var health: String? = null
    private var sports: String? = null
    private var science: String? = null
    private var tech: String? = null
    private val TAG = NewsCategoryHeadlines::class.simpleName
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsCategoryHeadlinesBinding.inflate(inflater)
        headlinesAdapter = NewsTopHeadlinesAdapter(
            headlineClickListener(),
            viewClickListener(),
            requireContext())

        val args = arguments
        allNews = args?.getString(getString(R.string.key_all_news))
        business = args?.getString(getString(R.string.key_business))
        entertainment = args?.getString(getString(R.string.key_entertainment))
        health = args?.getString(getString(R.string.key_health))
        sports = args?.getString(getString(R.string.key_sports))
        science = args?.getString(getString(R.string.key_science))
        tech = args?.getString(getString(R.string.key_tech))

        binding.rvNewsHeadlines.apply {
            adapter = headlinesAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            addOnItemTouchListener(rvTouchListener)

                when {
                    allNews == getString(R.string.all_news_label).lowercase() -> {
                        mainViewModel.topGeneralNews.observe(viewLifecycleOwner){
                            if (it!=null){
                                binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                            }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)
                        }
                    }
                    business == getString(R.string.business_label).lowercase() -> {
                        mainViewModel.topBusinessNews.observe(viewLifecycleOwner){
                            if (it!=null){
                            binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                        }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)

                        }
                    }
                    entertainment == getString(R.string.entertainment_label).lowercase() -> {
                        mainViewModel.topEntertainmentNews.observe(viewLifecycleOwner){
                            if (it!=null){
                                binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                            }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)
                        }
                    }
                    health == getString(R.string.health_label).lowercase() -> {
                        mainViewModel.topHealthNews.observe(viewLifecycleOwner){
                            if (it!=null){
                                binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                            }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)

                        }
                    }
                    sports == getString(R.string.sports_label).lowercase() -> {
                        mainViewModel.topSportNews.observe(viewLifecycleOwner){
                            if (it!=null){
                                binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                            }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)
                        }
                    }
                    science == getString(R.string.science_label).lowercase() -> {
                        mainViewModel.topScienceNews.observe(viewLifecycleOwner){
                            if (it!=null){
                                binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                            }
                            headlinesAdapter.submitList(null)
                            headlinesAdapter.submitList(it.articles)
                        }
                    }
                     else -> {
                         mainViewModel.topTechnologyNews.observe(viewLifecycleOwner){
                             if (it!=null){
                                 binding.pbLoadingTopHeadlines.visibility = View.INVISIBLE
                             }
                             headlinesAdapter.submitList(null)
                             headlinesAdapter.submitList(it.articles)
                         }
                    }
                }
        }

        return binding.root
    }

    private val rvTouchListener = object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val action = e.action
            return if (binding.rvNewsHeadlines.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                when (action) {
                    MotionEvent.ACTION_MOVE -> {
                        rv.parent.parent.parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
                false
            } else {
                when (action) {
                    MotionEvent.ACTION_MOVE ->
                        rv.parent.parent.parent.requestDisallowInterceptTouchEvent(false)
                }
                binding.rvNewsHeadlines.removeOnItemTouchListener(this)
                true
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    }

    private fun headlineClickListener() = NewsTopHeadlinesAdapter.HeadlineClickListener { article ->
        findNavController().navigate(
            DashboardDirections.actionDashboardToNewsDetail(article)
        )
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Suppress("DEPRECATION")
    private fun viewClickListener()= NewsTopHeadlinesAdapter.ViewClickListener { article, imgBtn  ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (imgBtn as ImageButton).imageTintList = resources.getColorStateList(R.color.purple_200, null)
        }else{
            (imgBtn as ImageButton).imageTintList = resources.getColorStateList(R.color.purple_200)
        }
        mainViewModel.bookmarkAnArticle(article)
    }
}

