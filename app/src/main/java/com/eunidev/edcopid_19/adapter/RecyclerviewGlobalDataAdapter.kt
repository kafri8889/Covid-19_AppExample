package com.eunidev.edcopid_19.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.activity.MainActivity
import com.eunidev.edcopid_19.model_data.CovidModel

class RecyclerviewGlobalDataAdapter(val context: Context, val list: ArrayList<CovidModel.ParentCG>) : RecyclerView.Adapter<RecyclerviewGlobalDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.cardViewGlobalData)
        val tvCountryName = itemView.findViewById<TextView>(R.id.tvCountryName_CardViewGlobalData)
        val tvConfirmed = itemView.findViewById<TextView>(R.id.tvConfirmed_CardViewGlobalData)
        val tvRecovery = itemView.findViewById<TextView>(R.id.tvRecovery_CardViewGlobalData)
        val tvDeaths = itemView.findViewById<TextView>(R.id.tvDeaths_CardViewGlobalData)
        val tvActive = itemView.findViewById<TextView>(R.id.tvActive_CardViewGlobalData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_globaldata, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentdata = list[position]

        holder.tvCountryName.text = currentdata.attributes.countryRegion
        holder.tvConfirmed.text = currentdata.attributes.confirmed.toString()
        holder.tvRecovery.text = currentdata.attributes.recovered.toString()
        holder.tvDeaths.text = currentdata.attributes.deaths.toString()
        holder.tvActive.text = currentdata.attributes.active.toString()

        holder.cardView.setOnLongClickListener { v ->
            val pop = PopupMenu(context, v)
            pop.menuInflater.inflate(R.menu.menu_pop, pop.menu)
            pop.setOnMenuItemClickListener {
                if (it.itemId == R.id.info_MenuPopUp) {
                    (context as MainActivity).showInfoCountry(currentdata)
                }

                return@setOnMenuItemClickListener true
            }

            pop.show()

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = list.size
}