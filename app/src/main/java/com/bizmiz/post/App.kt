package com.bizmiz.post

import android.app.Application
import com.bizmiz.post.di.module
import com.bizmiz.post.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            val module = listOf(module, viewModelModule)
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(module)
        }
    }
}