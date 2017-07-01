package com.samuelepontremoli.ktry.network

/**
 * Created by samuele on 01/07/17.
 * Singleton provider for the Giphy repository
 */

object GiphyRepositoryProvider {
    fun provideGiphyRepository(): GiphyRepository {
        return GiphyRepository(GiphyService.Factory.create())
    }
}