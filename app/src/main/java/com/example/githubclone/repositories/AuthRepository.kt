package com.example.githubclone.repositories

import com.example.githubclone.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAccessToken(clientID: String, clientSecret: String, code: String) =
        apiService.getAccessToken(clientID, clientSecret, code)
}
