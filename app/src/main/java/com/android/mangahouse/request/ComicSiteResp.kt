package com.android.mangahouse.request

class ComicSiteResp(val configs: List<Data>) {
    inner class Data (val proxy: String,
                      val r18: Boolean,
                      val site: String,
                      val source_index: String,
                      val source_name: String)
}
