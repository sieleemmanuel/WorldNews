package com.siele.worldnews.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.siele.worldnews.ui.NewsCategoryHeadlines

class NewsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 7
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> NewsCategoryHeadlines()
            else -> NewsCategoryHeadlines()
        }
    }
}