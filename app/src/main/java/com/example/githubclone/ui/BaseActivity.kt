package com.example.githubclone.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.githubclone.databinding.CustomDialogLoadingBinding

// this activity is a base activity for all activities
open class BaseActivity : AppCompatActivity() {

    private lateinit var dialogBinding: CustomDialogLoadingBinding
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        dialogBinding = CustomDialogLoadingBinding.inflate(layoutInflater)
        progressDialog = AlertDialog.Builder(this).create()
        progressDialog.setCancelable(false)
        progressDialog.setView(dialogBinding.root)
    }

     fun showDialog() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    fun dismissDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}
