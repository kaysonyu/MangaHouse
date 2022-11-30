package com.android.mangahouse

//class ComicContentResp (val msg: String, val code: Int, val count: Int, val data: List<String>)

class ComicContentResp (val chapter_number: Int,
                        val comicid: String,
                        val image_urls: List<String>,
                        val site: String,
                        val source_name: String,
                        val source_url: String,
                        val title: String)