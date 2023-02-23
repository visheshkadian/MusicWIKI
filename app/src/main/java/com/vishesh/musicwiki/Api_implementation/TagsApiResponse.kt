package com.vishesh.musicwiki.Api_implementation

import com.squareup.moshi.Json

class TagsApiResponse(

    var tags: TagsDataResponse
)

class TagsDataResponse(
    var tag: MutableList<TagsListResponse>
)

class TagsListResponse(

    var name: String,
    var tags: String,
    var url: String,
    var reach: String,
    var taggings: String,
    var streamable: String

)

// tag details..

class TagDetailsApiResponse(val tag: TagDetailsData)

class TagDetailsData(
    val name: String,
    val wiki: TagDetailsWiki?
)
class TagDetailsWiki(
    val summary: String,
    val content: String
)

// top albums list

class AlbumApiResponse(

    var albums: AlbumDataResponse
)

class AlbumDataResponse(
    var album: MutableList<AlbumListResponse>
)

class AlbumListResponse(
    val name: String,
    val mbid: String,
    var artist: AlbumAtristData,
    var image: MutableList<ImageData>

)

class AlbumAtristData(
    var name: String,
    var mbid: String,
    var url: String
)

class ImageData(

    @field:Json(name = "#text") val text: String,
    var size: String

)

// top artist ..

class ArtistApiResponse(

    var topartists: ArtistDataResponse
)

class ArtistDataResponse(
    var artist: MutableList<ArtistListResponse>
)

class ArtistListResponse(

    var name: String,
    var mbid: String,
    var url: String,
    var image: MutableList<ImageData>

)

// / top tracks

class TrackApiResponse(

    var tracks: TrackDataResponse
)

class TrackDataResponse(
    var track: MutableList<TrackListResponse>
)

class TrackListResponse(

    var name: String,
    var artist: TrackAtristData,
    var duration: String,
    var mbid: String,
    var image: MutableList<ImageData>

)

class TrackAtristData(
    var name: String,
    var mbid: String,
    var url: String
)

// / album details

class AlbumDetailsApiResponse(
    var album: AlbumDetailsListResponse
)

class AlbumDetailsListResponse(

    var name: String,
    var artist: String,
    var tags: TagsDataResponse
)

// /... artist details

class ArtistDetailsApiResponse(
    var artist: ArtistDetailsListResponse
)

class ArtistDetailsListResponse(

    var name: String,
    var stats: statsData,
    var tags: TagsDataResponse,
    var bio: SummaryData

)

class SummaryData(
    var summary: String
)

class statsData(
    var listeners: String,
    var playcount: String
)

// get artrist top album

class ArtistTopAlbumsApiResponse(
    var topalbums: AlbumDataResponse
)

class ArtistTopTracksApiResponse(
    var toptracks: TrackDataResponse
)
