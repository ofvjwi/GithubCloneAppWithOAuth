package com.example.githubclone.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    @SerializedName("login")
    val username: String,
    val name: String,
    val email: String,
    val bio: String,
    val location: String,
    val followers: Int,
    val following: Int,
): Parcelable
