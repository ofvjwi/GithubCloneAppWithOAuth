package com.example.githubclone.ui.explore.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclone.adapters.ForYouAndTrendingAdapter
import com.example.githubclone.databinding.FragmentTrendingBinding
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.utils.Extensions.setUpColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : BaseFragment() {
    private lateinit var binding: FragmentTrendingBinding
    private lateinit var adapter: ForYouAndTrendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = ForYouAndTrendingAdapter()
        adapter.submitList(prepareModelsList())
        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            swipeRefreshLayout.setUpColors(requireContext())
        }
    }

}