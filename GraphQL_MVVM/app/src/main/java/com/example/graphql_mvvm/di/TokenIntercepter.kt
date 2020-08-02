package com.example.graphql_mvvm.di

import dagger.Module
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenIntercepter @Inject constructor() : Interceptor{
    var token : String = ""
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization",token)
            .build()

        return chain.proceed(request)
    }
}