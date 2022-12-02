package com.android.mangahouse.request

//class ComicChapterResp (val msg: String, val code: Int, val count: Int, val data: ComicChapterResp.Data) {
//    inner class Data (val comicId: String, val title: String, val descs: String, val cover: String, val author: String, val comicType: String, val updateTime: String, val chapterList: List<ComicChapterResp.Data.Chapter>) {
//        inner class Chapter (val title: String, val chapterId: String)
//    }
//}

class ComicChapterResp (val author: String,
                        val chapters: List<Chapter>,
                        val comicid: String,
                        val cover_image_url: String,
                        val crawl_time: String,
                        val desc: String,
                        val ext_chapters: List<Ext>,
                        val last_update_time: String,
                        val name: String,
                        val site: String,
                        val source_name: String,
                        val source_url: String,
                        val status: String,
                        val tag: String,
                        val tags: List<Tag>) {

    inner class Chapter (val chapter_number: Int,
                         val source_url: String,
                         val title: String)

    inner class Ext (val ext_name: String,
                        val chapters: List<Chapter>)

    inner class Tag (val name: String,
                        val tag: String)
}