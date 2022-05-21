package com.example.githubclone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.githubclone.adapters.ProfileRepositoriesRvAdapter
import com.example.githubclone.data.local.prefs.SharedPrefs
import com.example.githubclone.databinding.FragmentProfileBinding
import com.example.githubclone.models.User
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponse
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponseItem
import com.example.githubclone.models.users_search_response.UsersResponse
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.utils.Extensions.fireToast
import com.example.githubclone.utils.Extensions.setUpLayoutManagerToLinearHorizontal
import com.example.githubclone.utils.Logger
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: ProfileRepositoriesRvAdapter

    private val viewModel by viewModels<ProfileViewModel>()
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
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        _viewLifecycleOwner = viewLifecycleOwner
        baseActivity = requireActivity() as BaseActivity
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        adapter = ProfileRepositoriesRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearHorizontal(requireContext())
        viewModel.getUserAllData(sharedPrefs.accessToken!!)
        setupObserver()
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.getUserAllData(sharedPrefs.accessToken!!) }
    }

    private fun setupObserver() {
        viewModel.userAllData.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.dismissDialog()
                    binding.swipeRefreshLayout.isRefreshing = false
                    it.data?.let { triple -> setDataToUI(triple) }
                }
                Status.LOADING -> {
                    baseActivity.showDialog()
                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.dismissDialog()
                    Logger.d(TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setDataToUI(triple: Triple<UsersResponse, List<UserRepositoriesResponseItem>, User>) {
        val usersResponse = triple.first
        val userRepositoriesResponse = triple.second
        val user = triple.third
        val firstUser = usersResponse.items?.get(0)

        binding.apply {
            Glide.with(binding.root.context).load(firstUser?.avatarUrl).into(imageViewProfile)
            textViewFullname.text = user.name
            textViewUsername.text = firstUser?.login
            textViewBio.text = user.bio
            textViewLocation.text = "\uD83D\uDCCD" + user.location
            textViewFollowersFollowing.text =
                "Ⓒ ${user.followers} followers • ${user.following} following"
            textViewCountOfRepositories.text =
                userRepositoriesResponse.size.toString()

        }

        adapter.submitList(userRepositoriesResponse)
        binding.recyclerView.adapter = adapter
    }
}
