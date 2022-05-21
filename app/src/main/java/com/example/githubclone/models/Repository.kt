package com.example.githubclone.models

import com.google.gson.annotations.SerializedName

data class Repository(
    val id: Int,
    val name: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    val language: String
)
