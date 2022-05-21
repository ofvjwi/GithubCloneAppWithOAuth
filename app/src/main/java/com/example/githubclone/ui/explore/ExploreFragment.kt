package com.example.githubclone.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.githubclone.R
import com.example.githubclone.adapters.ExploreViewPagerAdapter
import com.example.githubclone.databinding.FragmentExploreBinding
import com.example.githubclone.databinding.FragmentHomeBinding
import com.example.githubclone.ui.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter: ExploreViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = ExploreViewPagerAdapter(requireActivity())

        binding.apply {
            viewPager2.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position -> // Styling each tab here
                tab.text =
                    if (position == 0) getString(R.string.txt_for_you).uppercase()
                    else getString(R.string.txt_trending).uppercase()
            }.attach()

            viewPager2.apply {
                (getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
            }
        }
    }
}
