package com.kirandroid.gardenmonitor.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.activities.ScanPlantsActivity
import com.kirandroid.gardenmonitor.models.PlantData
import com.kirandroid.gardenmonitor.models.PlantOrganImageData
import org.w3c.dom.Text


class PlantAdapter(private val context: Context, private val plantsList: ArrayList<PlantData>):
    RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.layout_plant,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(plantsList.get(position).plantImageUrl).into(holder.plantIcon)
        holder.plantDate.setText(plantsList.get(position).date)
        holder.plantName.setText(plantsList.get(position).plantScientificName)

    }

    override fun getItemCount(): Int {
        return plantsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val plantDate = itemView.findViewById<TextView>(R.id.plantDate)
        val plantIcon = itemView.findViewById<ImageView>(R.id.plantImage)
        val plantName = itemView.findViewById<TextView>(R.id.txtPlantName)
    }

}