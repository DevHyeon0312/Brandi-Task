package com.devhyeon.kakaoimagesearch

import android.app.Application
import com.devhyeon.kakaoimagesearch.di.network.NetworkModule
import com.devhyeon.kakaoimagesearch.di.viewmodel.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        /** Koin */
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    NetworkModule,
                    ViewModelModule
                )
            )
        }
    }
}