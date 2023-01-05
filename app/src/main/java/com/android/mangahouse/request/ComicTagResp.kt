package com.android.mangahouse.request

class ComicTagResp(val tags: List<TagCategory>) {
    inner class TagCategory (val category: String, val tags: List<Tag>) {
        inner class Tag (val name: String, val tag: String)
    }
}
