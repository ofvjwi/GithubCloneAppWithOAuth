package com.example.githubclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclone.databinding.ItemRvForYouBinding
import com.example.githubclone.models.ForYouAndTrendingModel

// this adapter is "for For" You and "Trending" pages recycler view
class ForYouAndTrendingAdapter : BaseAdapter() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class Vh(var binding: ItemRvForYouBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 0) TYPE_1 else TYPE_2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemRvForYouBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_1) {
            if (holder is Vh) {
                holder.binding.imageView.visibility = View.GONE
            }
        } else {
            if (holder is Vh) {
                holder.binding.imageView.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(list: List<ForYouAndTrendingModel>) {
        dif.submitList(list)
    }

    companion object {
        private const val TYPE_1: Int = 0
        private const val TYPE_2: Int = 1
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ForYouAndTrendingModel>() {
            override fun areItemsTheSame(
                oldItem: ForYouAndTrendingModel,
                newItem: ForYouAndTrendingModel
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: ForYouAndTrendingModel,
                newItem: ForYouAndTrendingModel
            ): Boolean {
                return true
            }
        }
    }
}

