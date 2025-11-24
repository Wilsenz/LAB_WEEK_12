package com.example.test_lab_week_12.api

import retrofit2.http.GET
import com.example.test_lab_week_12.model.PopularMoviesResponse
import retrofit2.http.Query
interface MovieService {
    @GET("movie/popular")


    // suspended functions can be paused and resumed at a later time
    // this is useful for network calls, since they can take a long time to

    // and we don't want to block the main thread
    // for more info, see:
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): PopularMoviesResponse
}