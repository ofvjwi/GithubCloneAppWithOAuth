package com.example.githubclone.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.githubclone.R
import com.example.githubclone.databinding.ActivityMainBinding
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.explore.ExploreFragment
import com.example.githubclone.ui.explore.details.ForYouFragment
import com.example.githubclone.ui.explore.details.TrendingFragment
import com.example.githubclone.ui.home.HomeFragment
import com.example.githubclone.ui.notification.NotificationFragment
import com.example.githubclone.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setupWithNavController(navController)
        workWithBottomNavView()
    }

    // this function does not cause the bottom navigation view UI work strangely but it does not work with animation
    private fun setupNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> showBottomNav()
                R.id.notificationFragment -> showBottomNav()
                // and more
                else -> hideBottomNav()
            }
        }
    }


    // this function causes the bottom navigation view UI work strangely
    private fun workWithBottomNavView() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fragmentManager: FragmentManager,
                fragment: Fragment,
                view: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    Slide(Gravity.BOTTOM).excludeTarget(R.id.nav_host_fragment, true)
                )
                when (fragment) {
                    is HomeFragment -> {
                        showBottomNav()
                    }
                    is NotificationFragment -> {
                        showBottomNav()
                    }
                    is ExploreFragment -> {
                        showBottomNav()
                    }
                    is ProfileFragment -> {
                        showBottomNav()
                    }
                    is ForYouFragment -> {
                        showBottomNav()
                    }
                    is TrendingFragment -> {
                        showBottomNav()
                    }
                    else -> {
                        hideBottomNav()
                    }
                }
            }
        }, true)
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.GONE

    }
}


