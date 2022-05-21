package com.example.githubclone.ui.home.details.repositories.comments

import androidx.lifecycle.ViewModel
import com.example.githubclone.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

}