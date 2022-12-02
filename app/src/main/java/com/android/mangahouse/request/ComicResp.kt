package com.android.mangahouse.request

//class ComicResp (val msg: String, val code: Int, val count: Int, val data: List<ComicResp.Data>) {
//    inner class Data (val comicId: String, val title: String, val author: String, val comicType: String, val descs: String, val cover: String, val updateTime: String)
//}

class ComicResp (val search_result: List<Data>) {
    inner class Data (val comicid: String,
                      val cover_image_url: String,
                      val name: String,
                      val site: String,
                      val source_name: String,
                      val source_url: String,
                      val status: String)
}

