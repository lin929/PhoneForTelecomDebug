package com.github.arekolek.phone

import android.app.Application
import android.content.Context

import timber.log.Timber

class App : Application() {



    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        /*
        var data = IntArray(40 * 1024 * 1024)
        for (i in data.indices) {
            data[i] = i
        }
         */

    }

    init {
        instance = this
    }


    companion object {

        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}
