package com.example.githubclone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubclone.databinding.ItemRvSearchRepositoriesBinding
import com.example.githubclone.models.repositories_search_response.ItemsItem
import com.example.githubclone.models.repositories_search_response.RepositoriesResponse

// this adapter is for search page' repos
class SearchRepositoriesRvAdapter() : BaseAdapter() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class ViewHolderForSearchRepositories(var binding: ItemRvSearchRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val userRepositories = dif.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(userRepositories.owner?.avatarUrl).into(imageViewProfile)
                textViewUsername.text = userRepositories.owner?.login
                textViewRepoName.text = userRepositories.name
                textViewRepoBioName.text = userRepositories.commentsUrl.toString()
                textViewRepoDetails.text =
                    "⭐ ${userRepositories.stargazersCount} \uD83D\uDD35 ${userRepositories.language}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRvSearchRepositoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolderForSearchRepositories(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderForSearchRepositories) {
            (holder as ViewHolderForSearchRepositories).onBind(position)
        }
    }

    override fun getItemCount(): Int = dif.currentList.size


    fun submitList(repositoriesResponse: RepositoriesResponse?) {
        dif.submitList(repositoriesResponse?.items)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return true
            }
        }
    }
}
