package com.kirandroid.gardenmonitor.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.models.PlantData

class WaterNowPlantsAdapter(private val context: Context, private val plantsList: List<PlantData>): RecyclerView.Adapter<WaterNowPlantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.layout_water_now_card,parent,false)
        return WaterNowPlantsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.plantName!!.text = plantsList[position].plantScientificName
        holder.plantDate!!.text = plantsList[position].date

        var status = "Not Watered"

        // on click listener for ImageView
        holder.waterDropIcon!!.setOnClickListener {

            if(status.equals("Watered")) {
                holder.waterDropIcon.setImageResource(R.drawable.drop)
                status = "Not Watered"
            } else {
                status = "Watered"
                holder.waterDropIcon.setImageResource(R.drawable.drop_colored)

            }

        }
    }

    override fun getItemCount(): Int {
        return  plantsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val plantName: TextView? = itemView.findViewById<TextView>(R.id.plantName)
        val plantDate: TextView? = itemView.findViewById<TextView>(R.id.plantDate)
        val waterDropIcon: ImageView? = itemView.findViewById<ImageView>(R.id.waterDropIcon)

    }
}