package com.example.githubclone.models

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    val accessToken: String,
)
