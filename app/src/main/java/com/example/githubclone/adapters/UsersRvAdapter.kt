package com.example.githubclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubclone.databinding.ItemRvRepositoriesBinding
import com.example.githubclone.models.users_search_response.ItemsItem
import com.example.githubclone.models.users_search_response.UsersResponse

// this adapter is for search page' user data
class UsersRvAdapter : BaseAdapter() {
    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    inner class Vh(var binding: ItemRvRepositoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val user = dif.currentList[position]

            binding.apply {
                Glide.with(binding.root.context).load(user.avatarUrl).into(imageViewProfile)
                textViewUsername.text = user.reposUrl
                textViewRepositoryName.text = user.login
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemRvRepositoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Vh(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is Vh) holder.onBind(position)
    }

    override fun getItemCount(): Int = dif.currentList.size

    fun submitList(response: UsersResponse?) {
        dif.submitList(response?.items)
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