package com.siele.worldnews.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.siele.worldnews.R
import com.siele.worldnews.ui.NewsCategoryHeadlines
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsPagerAdapter constructor(
    fragmentManager: FragmentManager, 
    lifecycle: Lifecycle,
    val context: Context):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 7
    }
    override fun createFragment(position: Int): Fragment {
        val args = Bundle()
        return when(position){
            0 -> {
                args.putString(context.getString(R.string.key_all_news), context.getString(R.string.all_news_label).lowercase())
                val allNews = NewsCategoryHeadlines()
                allNews.arguments = args
                allNews
            }
            1 -> {
                args.putString(context.getString(R.string.key_business), context.getString(R.string.business_label).lowercase())
                val business = NewsCategoryHeadlines()
                business.arguments = args
                business
            }
            2 -> {
                args.putString(context.getString(R.string.key_entertainment), context.getString(R.string.entertainment_label).lowercase())
                val entertainment = NewsCategoryHeadlines()
                entertainment.arguments = args
                entertainment
            }
            3 -> {
                args.putString(context.getString(R.string.key_health), context.getString(R.string.health_label).lowercase())
                val health = NewsCategoryHeadlines()
                health.arguments = args
                health
            }
            4 -> {
                args.putString(context.getString(R.string.key_sports), context.getString(R.string.sports_label).lowercase())
                val sports = NewsCategoryHeadlines()
                sports.arguments = args
                sports
            }
            5 -> {
                args.putString(context.getString(R.string.key_science), context.getString(R.string.science_label).lowercase())
                val science = NewsCategoryHeadlines()
                science.arguments = args
                science
            }
            else -> {
                args.putString(context.getString(R.string.key_tech), context.getString(R.string.tech_label).lowercase())
                val tech = NewsCategoryHeadlines()
                tech.arguments = args
                tech
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}