package com.example.githubclone.ui.home.details.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.githubclone.adapters.SearchRepositoriesRvAdapter
import com.example.githubclone.adapters.UsersRvAdapter
import com.example.githubclone.data.local.prefs.SharedPrefs
import com.example.githubclone.databinding.FragmentSearchBinding
import com.example.githubclone.models.repositories_search_response.RepositoriesResponse
import com.example.githubclone.models.users_search_response.UsersResponse
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.BaseFragment
import com.example.githubclone.ui.home.details.repositories.RepositoriesFragment
import com.example.githubclone.ui.profile.ProfileFragment
import com.example.githubclone.utils.Extensions.fireToast
import com.example.githubclone.utils.Extensions.setUpLayoutManagerToLinearVertical
import com.example.githubclone.utils.Logger
import com.example.githubclone.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapterRepos: SearchRepositoriesRvAdapter
    private lateinit var adapterUsers: UsersRvAdapter

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var baseActivity: BaseActivity
    private lateinit var _viewLifecycleOwner: LifecycleOwner

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        baseActivity = requireActivity() as BaseActivity
        _viewLifecycleOwner = viewLifecycleOwner
        adapterRepos = SearchRepositoriesRvAdapter()
        adapterUsers = UsersRvAdapter()
        binding.recyclerView.setUpLayoutManagerToLinearVertical(requireContext())
        sharedPrefs = SharedPrefs.getInstance(requireContext())
        binding.searchEdittext.requestFocus()

        binding.apply {
            searchEdittext.setOnEditorActionListener(OnEditorActionListener { textView, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }

    private fun performSearch() {
        val query = binding.searchEdittext.text.toString()

        if (query.isNotEmpty() && query.isNotBlank()) {
            when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioButton1.id -> {
                    baseActivity.showDialog()
                    viewModel.getRepositories(query)
                    setupRepositoriesObserver()
                }
                binding.radioButton2.id -> {
                    baseActivity.showDialog()
                    viewModel.getUsers(query)
                    setupUsersObserver()
                }
                else -> {
                    baseActivity.showDialog()
                    viewModel.getRepositories(query)
                    setupRepositoriesObserver()
                }
            }
        }
    }

    private fun setupUsersObserver() {
        viewModel.usersResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.dismissDialog()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        setUsersDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    baseActivity.showDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.dismissDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setupRepositoriesObserver() {
        viewModel.repositoriesResponse.observe(_viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    baseActivity.dismissDialog()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.linearLayoutEmpty.visibility = View.GONE
                    it.data?.let { response ->
                        setReposDataToUI(response)
                    }
                }
                Status.LOADING -> {
                    baseActivity.showDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    baseActivity.dismissDialog()
                    binding.recyclerView.visibility = View.GONE
                    binding.linearLayoutEmpty.visibility = View.VISIBLE
                    Logger.d(ProfileFragment.TAG, it.message.toString())
                    fireToast(it.message.toString())
                }
            }
        }
    }

    private fun setReposDataToUI(reposResponse: RepositoriesResponse) {
        adapterRepos.submitList(reposResponse)
        binding.recyclerView.adapter = adapterRepos
    }

    private fun setUsersDataToUI(response: UsersResponse) {
        adapterUsers.submitList(response)
        binding.recyclerView.adapter = adapterUsers
    }
}
