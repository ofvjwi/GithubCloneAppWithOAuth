package com.example.githubclone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    // in the present case, in this app,  it would be better to write a single view model for all the fragments and activities
    // I didn't have a little time to add comments pages, I didn't move faster myself, my computer worked very, very slow
    // and a few small things were overlooked

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}

