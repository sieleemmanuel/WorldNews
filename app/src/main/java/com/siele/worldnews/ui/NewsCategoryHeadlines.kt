package com.siele.worldnews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.siele.worldnews.R
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.adapters.NewsTopHeadlinesAdapter
import com.siele.worldnews.databinding.FragmentNewsCategoryHeadlinesBinding
import com.siele.worldnews.utils.Constants

class NewsCategoryHeadlines : Fragment() {
    private lateinit var binding: FragmentNewsCategoryHeadlinesBinding
    private lateinit var headlinesAdapter: NewsTopHeadlinesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsCategoryHeadlinesBinding.inflate(inflater)
        headlinesAdapter = NewsTopHeadlinesAdapter(headlineClickListener(),requireContext())
        binding.rvNewsHeadlines.apply{
            adapter = headlinesAdapter
            layoutManager = LinearLayoutManager(requireContext(),HORIZONTAL, false)

        }
            headlinesAdapter.submitList(Constants.dummyNews)
            return binding.root
        }

    private fun headlineClickListener() = NewsTopHeadlinesAdapter.HeadlineClickListener { article ->
        findNavController().navigate(
            DashboardDirections.actionDashboardToNewsDetail(article)
        )
    }
}

