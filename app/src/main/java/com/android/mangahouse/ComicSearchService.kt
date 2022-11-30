package com.android.mangahouse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicSearchService {

//    @GET("comic/search/{option}/{key}/1/30")
//    fun getComicResp(@Path("option") option: String, @Path("key") key:String): Call<ComicResp>
//
//    @GET("comicChapter/search/{comicId}")
//    fun getComicChapterResp(@Path("comicId") comicId: String): Call<ComicChapterResp>
//
//    @GET("comicContent/search/{chapterId}")
//    fun getComicContentResp(@Path("chapterId") chapterId: String): Call<ComicContentResp>

    @GET("search")
    fun getComicResp(@Query("name") name:String): Call<ComicResp>

    @GET("comic/{comicId}")
    fun getComicChapterResp(@Path("comicId") comicId: String): Call<ComicChapterResp>

    @GET("comic/{comicId}/{chapterNumber}")
    fun getComicContentResp(@Path("comicId") comicId: String, @Path("chapterNumber") chapterNumber: Int): Call<ComicContentResp>

}