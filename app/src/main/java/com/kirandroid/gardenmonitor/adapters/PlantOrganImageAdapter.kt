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
import com.kirandroid.gardenmonitor.models.PlantOrganImageData


class PlantOrganImageAdapter(private val context: Context, private val plantOrgansList: ArrayList<PlantOrganImageData>):
    RecyclerView.Adapter<PlantOrganImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.layout_plant_organ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(plantOrgansList.get(position).organName.equals("addImage") && plantOrgansList.get(position).plantOrganUrl.equals("imageURL")) {

            holder.plantOrganImageLayout.visibility = View.GONE
            holder.placeholderLayout.visibility = View.VISIBLE

            holder.placeholderLayout.setOnClickListener {
                // back intent
                val intent = Intent(context,ScanPlantsActivity::class.java)
                context.startActivity(intent)
            }


        } else {

            holder.placeholderLayout.visibility = View.GONE
            holder.plantOrganImageLayout.visibility = View.VISIBLE

            Glide.with(context).load(plantOrgansList.get(position).plantOrganUrl).into(holder.organImage)
            holder.txtOrganName.setText(plantOrgansList.get(position).organName.toString())

            if(plantOrgansList.get(position).organName.equals("Leaf")) {
                Glide.with(context).load(R.drawable.leaf_icon).into(holder.organIcon)
            } else if(plantOrgansList.get(position).organName.equals("Flower")) {
                Glide.with(context).load(R.drawable.flower_icon).into(holder.organIcon)
            } else {
                Glide.with(context).load(R.drawable.apple_icon).into(holder.organIcon)
            }
        }
    }

    override fun getItemCount(): Int {
        return plantOrgansList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val txtOrganName = itemView.findViewById<TextView>(R.id.txtOrganName)
        val organIcon = itemView.findViewById<ImageView>(R.id.organIcon)
        val organImage = itemView.findViewById<ImageView>(R.id.imageView)
        val plantOrganImageLayout = itemView.findViewById<RelativeLayout>(R.id.plantOrganLayout)
        val placeholderLayout = itemView.findViewById<RelativeLayout>(R.id.placeHolderLayout)

    }

}