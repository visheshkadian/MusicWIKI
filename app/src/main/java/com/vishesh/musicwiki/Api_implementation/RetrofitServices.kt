package com.rekord.movieapplication.Retrofit

import com.vishesh.musicwiki.Api_implementation.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("2.0")
    fun getTagsApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String
    ): Call<TagsApiResponse>

    @GET("2.0")
    fun getTagsDetailsApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("tag") tag: String

    ): Call<TagDetailsApiResponse>

    @GET("2.0")
    fun getAlbumApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("tag") tag: String

    ): Call<AlbumApiResponse>

    @GET("2.0")
    fun getArtistApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("tag") tag: String

    ): Call<ArtistApiResponse>

    @GET("2.0")
    fun getTracksApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("tag") tag: String

    ): Call<TrackApiResponse>

    @GET("2.0")
    fun getAlbumDetailsApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("artist") artist: String,
        @Query("album") album: String

    ): Call<AlbumDetailsApiResponse>

    @GET("2.0")
    fun getArtistDetailsApi(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("artist") artist: String
    ): Call<ArtistDetailsApiResponse>

    @GET("2.0")
    fun getArtistTopAlbums(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("artist") artist: String
    ): Call<ArtistTopAlbumsApiResponse>

    @GET("2.0")
    fun getArtistTopTracks(
        @Query("api_key") apikey: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("artist") artist: String
    ): Call<ArtistTopTracksApiResponse>
}
