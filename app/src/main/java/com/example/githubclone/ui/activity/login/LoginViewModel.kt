package com.example.githubclone.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubclone.models.AccessToken
import com.example.githubclone.repositories.AuthRepository
import com.example.githubclone.utils.Constants.clientID
import com.example.githubclone.utils.Constants.clientSecret
import com.example.githubclone.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    companion object {
        private val TAG: String = LoginViewModel::class.java.simpleName.toString()
    }

    val accessToken = MutableLiveData<AccessToken>()

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            try {
                accessToken.value = repository.getAccessToken(clientID, clientSecret, code)
                Logger.d(TAG, "AccessToken: ${accessToken.value?.accessToken}")
            } catch (exception: Exception) {
                Logger.e(TAG, "getAccessToken: error $exception")
            }
        }
    }
}
