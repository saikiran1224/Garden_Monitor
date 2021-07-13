package com.kirandroid.gardenmonitor.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.models.PlantAttrData
import com.kirandroid.gardenmonitor.models.PlantData
import org.w3c.dom.Text


class WikiPlantAttrAdapter(private val context: Context, private val plantAttrList: ArrayList<PlantAttrData>):
    RecyclerView.Adapter<WikiPlantAttrAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.layout_plant_attributes,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.attrIcon.setImageDrawable(plantAttrList.get(position).attrIcon)
        holder.attrTitle.text = plantAttrList.get(position).attrTitle.toString()
        holder.attrValue.text = plantAttrList.get(position).attrValue.toString()

    }

    override fun getItemCount(): Int {
        return plantAttrList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val attrIcon = itemView.findViewById<ImageView>(R.id.attrIcon)
        val attrTitle = itemView.findViewById<TextView>(R.id.attrTitle)
        val attrValue = itemView.findViewById<TextView>(R.id.attrValue)

    }

}