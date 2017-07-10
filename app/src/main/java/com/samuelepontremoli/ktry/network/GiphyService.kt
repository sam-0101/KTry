package com.samuelepontremoli.ktry.network

import com.google.gson.GsonBuilder
import com.samuelepontremoli.ktry.utils.BASE_URL
import com.samuelepontremoli.ktry.utils.DEBUG
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by samuele on 01/07/17.
 * Retrofit Service to call Giphy API
 */
interface GiphyService {

    @GET("v1/gifs/trending")
    fun getTrending(@Query("api_key") apiKey: String): Flowable<getGiphyTrending>

    companion object Factory {

        fun create(): GiphyService {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(loggingInterceptor)

            val gson = GsonBuilder().create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create(GiphyService::class.java)
        }

    }

}