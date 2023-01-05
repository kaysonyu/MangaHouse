package com.android.mangahouse.request

import com.android.mangahouse.request.ComicChapterResp
import com.android.mangahouse.request.ComicContentResp
import com.android.mangahouse.request.ComicResp
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

    @GET("/aggregate/search")
    fun getComicResp(@Query("name") name:String, @Query("site") site:String): Call<ComicResp>

    @GET("/api/{site}/comic/{comicId}")
    fun getComicChapterResp(@Path("site") site: String, @Path("comicId") comicId: String): Call<ComicChapterResp>

    @GET("/api/{site}/comic/{comicId}/{chapterNumber}")
    fun getComicContentResp(@Path("site") site: String, @Path("comicId") comicId: String, @Path("chapterNumber") chapterNumber: Int): Call<ComicContentResp>

    @GET("/crawler/config")
    fun getAllSites(): Call<ComicSiteResp>


}