package com.boshra.moviebox.core.di

import com.boshra.moviebox.BuildConfig
import com.boshra.moviebox.framework.network.ApiService
import com.boshra.moviebox.framework.network.DateAdapter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        const val baseUrl = "baseUrl"
        const val authToken = "authToken"
    }

    @Named(baseUrl)
    @Provides
    @Singleton
    fun getBaseUrl():String{
        return BuildConfig.BASE_URL
    }

    @Named(authToken)
    @Provides
    @Singleton
    fun getAuthToken():String{
        return BuildConfig.TMDB_API_KEY
    }

    @Provides
    @Singleton
    fun provideInterceptor(@Named(authToken) token: String): ArrayList<Interceptor> {
        val interceptor = ArrayList<Interceptor>()
        val authInterceptor = Interceptor { chain ->
            val originalReq = chain.request()
            val newUrlBuilder = originalReq.url().newBuilder()
                .addQueryParameter("api_key",token)
            val newRequest = originalReq.newBuilder()
                .url(newUrlBuilder.build())
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
        interceptor.add(authInterceptor)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(@Named(Companion.baseUrl) baseUrl: String): Retrofit.Builder{
        // get json parser
        val gsonBuilder = GsonBuilder().setLenient()
        gsonBuilder.registerTypeAdapter(Date::class.java, DateAdapter())
        val gson = gsonBuilder.create()

        return Retrofit
            .Builder()
            .apply {
                addConverterFactory(GsonConverterFactory.create(gson))
                baseUrl(baseUrl)
            }
    }

    @Provides
    @Singleton
    fun provideOkHttpBuilder(interceptor: ArrayList<Interceptor>): OkHttpClient.Builder{
        val client = OkHttpClient.Builder()
        //add interceptors
        interceptor.takeIf { it.isNotEmpty() }?.forEach {
            client.addInterceptor(it)
        }
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit {
        return retrofitBuilder.client(okHttpClient.build()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}