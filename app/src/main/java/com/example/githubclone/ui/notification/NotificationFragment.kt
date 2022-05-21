package com.example.githubclone.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubclone.databinding.FragmentNotificationBinding
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.utils.Extensions.setUpColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.swipeRefreshLayout.setUpColors(requireContext())
    }
}

