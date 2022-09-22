package com.siele.worldnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.siele.worldnews.databinding.ArticleLoaderItemBinding

class LoaderAdapter(private val retry:()->Unit): LoadStateAdapter<LoaderAdapter.LoaderViewHolder>(){

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder.getInstance(parent, retry)
    }

    class LoaderViewHolder(private val binding: ArticleLoaderItemBinding, retry:()->Unit): RecyclerView.ViewHolder(binding.root) {
        companion object{
            fun getInstance(parent:ViewGroup, retry: () -> Unit):LoaderViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ArticleLoaderItemBinding.inflate(inflater, parent, false )
                return LoaderViewHolder(binding, retry)
            }
        }
        init {
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Loading){
                binding.root.transitionToEnd()
            }else{
                binding.root.transitionToStart()
            }
        }

    }

}