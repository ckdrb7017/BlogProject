package com.example.graphql_mvvm.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.coroutines.toFlow
import com.example.graphql_mvvm.R
import com.example.graphql_mvvm.di.ApplicationModule
import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.LoginMutation
import com.example.rocketreserver.TripsBookedSubscription
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

class DefaultRepository @Inject constructor(@ApplicationModule.ApiServerNetworkSource private val apolloClient : ApolloClient,
                                            @ApplicationModule.ApiRealTimeServerNetworkSource private val apolloRealTimeClient: ApolloClient,
                                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ApiRepository {

    override suspend fun getLaunchList(): Response<LaunchListQuery.Data>? {
        var result :  Response<LaunchListQuery.Data> ?= null
        withContext(ioDispatcher){
            val response = apolloClient.query(LaunchListQuery()).toDeferred().await()
            val launch = response.data?.launches
            Log.d("LaunchList", launch.toString())
            result = response

        }
        return result!!
    }

}