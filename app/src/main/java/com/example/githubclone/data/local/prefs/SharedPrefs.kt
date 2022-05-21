package com.example.githubclone.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// private constructor  is not right for dagger hilt but it is right for singleton class
class SharedPrefs @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences?

    companion object {
        private var sharedPrefs: SharedPrefs? = null
        fun getInstance(context: Context): SharedPrefs {
            if (sharedPrefs == null) {
                sharedPrefs = SharedPrefs(context)
            }
            return sharedPrefs!!
        }

        private const val PREF_NAME = "GithubClone"
        private const val PREF_MODE = Context.MODE_PRIVATE
        private val PREF_ACCESS_TOKEN = Pair("ACCESS_TOKEN", "")
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PREF_MODE)
    }

    var accessToken: String?
        get() = sharedPreferences!!.getString(PREF_ACCESS_TOKEN.first, PREF_ACCESS_TOKEN.second)
        set(value) {
            val editor = sharedPreferences!!.edit()
            editor.putString(PREF_ACCESS_TOKEN.first, value)
            editor.apply()
        }

    fun clearAll() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.apply()
    }
}

