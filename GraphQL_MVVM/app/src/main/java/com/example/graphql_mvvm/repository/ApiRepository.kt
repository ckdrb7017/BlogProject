package com.example.graphql_mvvm.repository

import android.content.Context
import com.apollographql.apollo.api.Response
import com.example.rocketreserver.LaunchListQuery
import com.example.rocketreserver.LoginMutation
import com.example.rocketreserver.TripsBookedSubscription


interface ApiRepository {

   suspend fun getLaunchList() : Response<LaunchListQuery.Data>?

}