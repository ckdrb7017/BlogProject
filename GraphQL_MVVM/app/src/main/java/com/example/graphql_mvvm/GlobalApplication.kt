package com.example.graphql_mvvm

import com.example.graphql_mvvm.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class GlobalApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private var instance: GlobalApplication? = null

        val globalApplicationContext: GlobalApplication
            get() {
                checkNotNull(instance) {}
                return instance!!
            }

        var token =""
    }

}
