package com.example.githubclone.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import com.example.githubclone.data.local.prefs.SharedPrefs
import com.example.githubclone.databinding.ActivitySplashBinding
import com.example.githubclone.ui.BaseActivity
import com.example.githubclone.ui.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var timer: CountDownTimer? = null

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        sharedPrefs = SharedPrefs.getInstance(this)
        setSplashTimer()
    }

    private fun setSplashTimer() {
        timer = object : CountDownTimer(2000, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                if (sharedPrefs.accessToken.isNullOrBlank()) callLoginActivity() else callMainActivity()
            }
        }
        timer?.start()
    }

    private fun callLoginActivity() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun callMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}