package com.example.githubclone.ui.home.details.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.githubclone.adapters.UserRepositoriesRvAdapter
import com.example.githubclone.data.local.prefs.SharedPrefs
import com.example.githubclone.databinding.FragmentRepositoriesBinding
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponse
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponseItem
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.ui.profile.ProfileFragment
import com.example.githubclone.utils.Extensions.fireToast
import com.example.githubclone.utils.Extensions.setUpColors
import com.example.githubclone.utils.Extensions.setUpLayoutManagerToLinearVertical
import com.example.githubclone.utils.Logger
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentRepositoriesBinding
    private lateinit var adapter: UserRepositoriesRvAdapter

    private val viewModel by viewModels<RepositoriesViewModel>()
    private lateinit var baseActivity: BaseActivity
    private lateinit var _viewLifecycleOwner: LifecycleOwner

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        baseActivity = requireActivity() as BaseActivity
        _viewLifecycleOwner = viewLifecycleOwner
        adapter = UserRepositoriesRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearVertical(requireContext())
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        viewModel.getUserRepositories(sharedPrefs.accessToken!!)
        setupObserver()
        binding.apply {
            swipeRefreshLayout.setUpColors(requireContext())
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getUserRepositories(sharedPrefs.accessToken!!)
            }
        }
    }

    private fun setupObserver() {
        viewModel.userRepositories.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.dismissDialog()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { response ->
                        setDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    baseActivity.showDialog()
                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.dismissDialog()
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setDataToUI(response: List<UserRepositoriesResponseItem>) {
        adapter.submitList(response)
        binding.recyclerView.adapter = adapter
    }
}

