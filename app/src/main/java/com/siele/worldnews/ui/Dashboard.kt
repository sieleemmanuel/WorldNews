package com.siele.worldnews.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.siele.worldnews.R
import com.siele.worldnews.adapters.ArticlePagingAdapter
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.adapters.LoaderAdapter
import com.siele.worldnews.adapters.NewsPagerAdapter
import com.siele.worldnews.data.model.Article
import com.siele.worldnews.databinding.FragmentDashboardBinding
import com.siele.worldnews.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@AndroidEntryPoint
class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var newsPagerAdapter:NewsPagerAdapter
    //private lateinit var articlePreviewAdapter: ArticlePreviewAdapter
    private lateinit var articlePagingAdapter: ArticlePagingAdapter
    //private lateinit var loaderAdapter: LoaderAdapter
    private val mainViewModel:MainViewModel by activityViewModels()
    private var currentFragment: Fragment? = null
    private val TAG = Dashboard::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentDashboardBinding.inflate(inflater)
       newsPagerAdapter = NewsPagerAdapter(childFragmentManager,lifecycle,requireContext())
        currentFragment = childFragmentManager.findFragmentByTag("f${
                binding.vpNewsHeadline.currentItem}")
        Log.d(TAG, "fragment: $currentFragment")
        articlePagingAdapter = ArticlePagingAdapter(
            articleClickListener(),
            viewClickListener(),
            )
       // loaderAdapter = LoaderAdapter { articlePagingAdapter.retry() }
        binding.apply {
            setUpRecyclerview()
            setUpToolbar()
            setUpViewPager()
            }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.getLatestNews().distinctUntilChanged().collectLatest { pagingArticles ->
                    articlePagingAdapter.submitData(pagingArticles)
                }
            }
        }
        articlePagingAdapter.addLoadStateListener { combinedLoadStates ->
            binding.pbLoadingLatestNews.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
        }

        /*mainViewModel.topHeadline.observe(viewLifecycleOwner){
        }*/
        return binding.root
    }

    private fun FragmentDashboardBinding.setUpViewPager() {
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

    private fun FragmentDashboardBinding.setUpRecyclerview() {
        rvLatestNews.apply {
            adapter = articlePagingAdapter/*.withLoadStateFooter(loaderAdapter)*/
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        rvSearchedNews.apply {
            adapter = articlePagingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

    }

    private fun FragmentDashboardBinding.setUpToolbar(
    ) {
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        dashboardToolbar.apply {
            setupWithNavController(findNavController(), appBarConfiguration)
            setOnMenuItemClickListener {
                false
            }
            val searchViewItem = menu.findItem(R.id.actionSearch)
            val searchView = searchViewItem.actionView as SearchView
            searchNews(searchView)

        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Suppress("DEPRECATION")
    private fun viewClickListener()= ArticlePagingAdapter.ViewClickListener { imgBtn, article ->
        if (article.isBookmarked == true){
            mainViewModel.bookmarkAnArticle(article, false)
        }else{
            mainViewModel.bookmarkAnArticle(article, true)
        }
    }

    private fun articleClickListener() = ArticlePagingAdapter.ArticleClickListener { article ->
        findNavController().navigate(DashboardDirections.actionDashboardToNewsDetail(article))
    }

    private fun searchNews(searchView: SearchView) {
        searchView.apply {
            queryHint = "Search News"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterArticles(query = query)
                    //(currentFragment!! as NewsCategoryHeadlines).filterHeadlines(query = query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterArticles(query = newText)
                    //(currentFragment!! as NewsCategoryHeadlines).filterHeadlines(query = newText)
                    return true
                }

            })
        }
    }

    private fun filterArticles(query: String?) {
        mainViewModel.getSearchedNews(query!!)
        mainViewModel.searchedNews.observe(viewLifecycleOwner){
            if (it!=null) {
                binding.apply {
                rvSearchedNews.visibility= View.VISIBLE
                tabLayout.visibility = View.INVISIBLE
                vpNewsHeadline.visibility = View.INVISIBLE
                tvLatestNews.visibility = View.INVISIBLE
                rvLatestNews.visibility = View.INVISIBLE
                }
                if (it.articles.isEmpty()) {
                    Toast.makeText(requireContext(), "No match found", Toast.LENGTH_SHORT).show()
                    //articlePreviewAdapter.submitList(null)

                } else {
                   // articlePreviewAdapter.submitList(null)
                   // articlePreviewAdapter.submitList(it.articles)
                }
            }else{
                binding.apply {
                    rvSearchedNews.visibility= View.GONE
                    tabLayout.visibility = View.VISIBLE
                    vpNewsHeadline.visibility = View.VISIBLE
                    tvLatestNews.visibility = View.VISIBLE
                    rvLatestNews.visibility = View.VISIBLE
                }
            }
        }
    }

}