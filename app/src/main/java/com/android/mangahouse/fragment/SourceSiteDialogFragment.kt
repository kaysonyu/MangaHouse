package com.android.mangahouse.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.android.mangahouse.R
import com.android.mangahouse.`object`.Site
import com.android.mangahouse.request.ComicSearchService
import com.android.mangahouse.request.ComicSiteResp
import com.android.mangahouse.request.ServiceCreator
import com.android.mangahouse.sql.SitesDao
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_source_site_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourceSiteDialogFragment : DialogFragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val sitesDao = SitesDao(activity)

        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        var allSites = ArrayList<ComicSiteResp.Data>()

        searchRespService.getAllSites().enqueue(object : Callback<ComicSiteResp> {
            override fun onResponse(call: Call<ComicSiteResp>, response: Response<ComicSiteResp>) {
                val comicSiteResp = response.body()
                if (comicSiteResp != null) {
                    allSites = ArrayList(comicSiteResp.configs)
                    allSites.forEach {
                        val chip = Chip(context)
                        chip.text = it.source_name
                        chip.tag = it.site
                        chip.isCheckable = true
                        chip.maxWidth = 280
                        source_site_chip_group.addView(chip)

                        if (sitesDao.isHasSite(Site(it.site, it.source_name))) {
                            chip.isChecked = true
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ComicSiteResp>, t: Throwable) {
                t.printStackTrace()
            }

        })

        confirm_button.setOnClickListener {
            sitesDao.deleteAll()
            val checkedChipIds = source_site_chip_group.checkedChipIds
            for (id in checkedChipIds) {
                val chip = source_site_chip_group.findViewById<Chip>(id)
                sitesDao.addSite(Site(chip.tag.toString(), chip.text.toString()))
            }

            dismiss()
        }
        
        
        super.onActivityCreated(savedInstanceState)
    }

    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_source_site_dialog, container, false)
    }


    private fun getComicSite(): ArrayList<ComicSiteResp.Data> {
        val searchRespService =
            ServiceCreator.create(
                ComicSearchService::class.java)
        val that = this
        var allSite = ArrayList<ComicSiteResp.Data>()

        searchRespService.getAllSites().enqueue(object : Callback<ComicSiteResp> {
            override fun onResponse(call: Call<ComicSiteResp>, response: Response<ComicSiteResp>) {
                val comicSiteResp = response.body()
                if (comicSiteResp != null) {
                    allSite = ArrayList(comicSiteResp.configs)
                }
            }

            override fun onFailure(call: Call<ComicSiteResp>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return allSite

    }
}
