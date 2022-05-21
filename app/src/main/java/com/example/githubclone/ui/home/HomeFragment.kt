package com.example.githubclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.githubclone.R
import com.example.githubclone.databinding.FragmentHomeBinding
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.utils.Extensions.setUpColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            swipeRefreshLayout.setUpColors(requireContext())

            itemRepositories.setOnClickListener {
                findNavController().navigate(R.id.repositoriesFragment)
            }

            imageViewSearch.setOnClickListener {
                findNavController().navigate(R.id.searchFragment)
            }
        }

    }
}
