package com.example.githubclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubclone.databinding.ItemRvRepositoriesBinding
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponseItem

// this adapter is for repos page' rv
class UserRepositoriesRvAdapter : BaseAdapter() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class ViewHolderForRepositories(var binding: ItemRvRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val userRepositories = dif.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(userRepositories.owner?.avatarUrl)
                    .into(imageViewProfile)
                textViewUsername.text = userRepositories.owner?.login
                textViewRepositoryName.text = userRepositories.name
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRvRepositoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolderForRepositories(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderForRepositories) holder.onBind(position)
    }

    override fun getItemCount(): Int = dif.currentList.size

    fun submitList(repositoriesResponse: List<UserRepositoriesResponseItem>) {
        dif.submitList(repositoriesResponse)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<UserRepositoriesResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserRepositoriesResponseItem,
                newItem: UserRepositoriesResponseItem
            ): Boolean {
                return true
            }

            override fun areContentsTheSame(
                oldItem: UserRepositoriesResponseItem,
                newItem: UserRepositoriesResponseItem
            ): Boolean {
                return true
            }
        }
    }
}

