package com.example.graphql_mvvm.di


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.example.graphql_mvvm.repository.ApiRepository
import com.example.graphql_mvvm.repository.DefaultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME


@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class ApiServerNetworkSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class ApiRealTimeServerNetworkSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class ApiLocalSource

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO


    @JvmStatic
    @Singleton
    @Provides
    fun provideOkhttp(tokenIntercepter: TokenIntercepter) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(tokenIntercepter)
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }


    @JvmStatic
    @Singleton
    @Provides
    @ApiServerNetworkSource
    fun provideApolloClient(okHttpClient: OkHttpClient) : ApolloClient {
        return  ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    @ApiRealTimeServerNetworkSource
    fun provideRealTimeApolloClient(okHttpClient: OkHttpClient) : ApolloClient {
        return  ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .subscriptionTransportFactory(WebSocketSubscriptionTransport.Factory("wss://apollo-fullstack-tutorial.herokuapp.com/graphql",okHttpClient))
            .build()
    }

}

@Module
abstract class ApplicationModuleBinds {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultRepository): ApiRepository
}

