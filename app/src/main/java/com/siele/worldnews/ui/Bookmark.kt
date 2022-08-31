package com.siele.worldnews.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.siele.worldnews.R
import com.siele.worldnews.adapters.ArticlePreviewAdapter
import com.siele.worldnews.databinding.FragmentBookmarkBinding
import com.siele.worldnews.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Bookmark : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var newsArticleAdapter: ArticlePreviewAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val TAG = Bookmark::class.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.getBookmarkedNews().observe(viewLifecycleOwner){
        newsArticleAdapter = ArticlePreviewAdapter(
            articleClickListener(),
            viewClickListener(),
            bookmarkListener(),
            requireContext())
        val appBarConfiguration = AppBarConfiguration(findNavController().graph)
        binding.apply {
            rvBookmark.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
            }
            bookmarkToolbar.setupWithNavController(findNavController(),appBarConfiguration)
        }

            Toast.makeText(requireContext(), "${it.size}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onViewCreated: $it")
            newsArticleAdapter.submitList(null)
            newsArticleAdapter.submitList(it)
        }
    }

    private fun articleClickListener() = ArticlePreviewAdapter.ArticleClickListener { article ->
        findNavController().navigate(BookmarkDirections.actionBookmarkToNewsDetail(article))
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