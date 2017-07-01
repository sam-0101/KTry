package com.samuelepontremoli.ktry.network

import com.samuelepontremoli.ktry.BASE_URL
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by samuele on 01/07/17.
 * Servizio Retrofit per chiamate all'API Giphy
 */
interface GiphyService {

    @GET("v1/gifs/trending")
    fun getTrending(@Query("api_key") apiKey: String): Flowable<getGiphyTrending>

    companion object Factory {

        fun create(): GiphyService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(GiphyService::class.java)
        }

    }

}