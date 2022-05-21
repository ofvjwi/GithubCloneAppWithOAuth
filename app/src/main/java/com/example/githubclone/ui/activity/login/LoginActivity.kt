package com.example.githubclone.ui.activity.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.githubclone.R
import com.example.githubclone.data.local.prefs.SharedPrefs
import com.example.githubclone.databinding.ActivityLoginBinding
import com.example.githubclone.databinding.CustomDialogLoadingBinding
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.activity.MainActivity
import com.example.githubclone.utils.Constants.clientID
import com.example.githubclone.utils.Constants.oauthLoginURL
import com.example.githubclone.utils.Extensions.fireToast
import com.example.githubclone.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    companion object {
        private val TAG: String = LoginActivity::class.java.simpleName.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        sharedPrefs = SharedPrefs.getInstance(this)
        binding.btnSignInWithGithub.setOnClickListener { processLogin() }

        viewModel.accessToken.observe(this) { accessToken ->
            sharedPrefs.accessToken = accessToken.accessToken
            Logger.d(TAG, "onCreate: access token: $accessToken")
            Logger.d(TAG, "shared: access token: ${sharedPrefs.accessToken}")
            callMainActivity()
        }
    }

    private fun callMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
      //  intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        getAccessToken()
    }

    private fun getAccessToken() {
        val uri: Uri? = intent?.data
        if (uri != null) {
            val code = uri.getQueryParameter(getString(R.string.txt_code))
            if (code != null) {
                showDialog()
                viewModel.getAccessToken(code)
                fireToast(getString(R.string.txt_login_success))
            } else {
                val error = uri.getQueryParameter(getString(R.string.txt_error))
                if (error != null) {
                    Logger.d(TAG, "error: $error")
                    fireToast(getString(R.string.txt_something_went_wrong))
                }
            }
        }
    }

    private fun processLogin() {
        showDialog()
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("$oauthLoginURL?client_id=$clientID&scope=repo"))
        startActivity(intent)
    }
}
