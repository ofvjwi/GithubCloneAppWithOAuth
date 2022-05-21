package com.example.githubclone.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclone.models.User
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponse
import com.example.githubclone.models.user_repos_response.UserRepositoriesResponseItem
import com.example.githubclone.models.users_search_response.UsersResponse
import com.example.githubclone.repositories.MainRepository
import com.example.githubclone.utils.Logger
import com.example.githubclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    companion object {
        private val TAG: String = ProfileViewModel::class.java.simpleName.toString()
    }

    val userAllData =
        MutableLiveData<Resource<Triple<UsersResponse, List<UserRepositoriesResponseItem>, User>>>()

    fun getUserAllData(token: String) {
        viewModelScope.launch {
            userAllData.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                Logger.e("tag","logger error30")
                val userAll = repository.getUsers(userData.username)
                Logger.e("tag","logger error32")
                val userRepositories = repository.getUserRepositories(token, userData.username)
                Logger.e("tag","logger error34")

                val triple = Triple(userAll, userRepositories, userData)
                userAllData.postValue(Resource.success(triple))
            } catch (exception: Exception) {
                userAllData.postValue(Resource.error("$exception", null))
            }
        }
    }
}


