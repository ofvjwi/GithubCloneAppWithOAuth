package com.example.githubclone.ui.home.details.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclone.models.User
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponse
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponseItem
import com.example.githubclone.models.users_search_response.UsersResponse
import com.example.githubclone.repositories.MainRepository
import com.example.githubclone.ui.profile.ProfileViewModel
import com.example.githubclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    companion object {
        private val TAG: String = ProfileViewModel::class.java.simpleName.toString()
    }
    val userRepositories =
        MutableLiveData<Resource<List<UserRepositoriesResponseItem>>>()

    fun getUserRepositories(token: String) {
        viewModelScope.launch {
            userRepositories.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                val repositories = repository.getUserRepositories(token, userData.username)
                userRepositories.postValue(Resource.success(repositories))
            } catch (exception: Exception) {
                userRepositories.postValue(Resource.error("$exception", null))
            }
        }
    }
}

