package com.kirandroid.gardenmonitor.adapters

import android.R.string
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.activities.ScanPlantsActivity
import com.kirandroid.gardenmonitor.models.PlantOrganImageData
import com.kirandroid.gardenmonitor.utils.AppPreferences


class PlantOrganImageAdapter(private val context: Context, private val plantOrgansList: ArrayList<PlantOrganImageData>):
    RecyclerView.Adapter<PlantOrganImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(R.layout.layout_plant_organ,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Showing Progress Indicator for loading Images
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorFilter(0xf25645E, PorterDuff.Mode.SRC_ATOP)
        circularProgressDrawable.start()

        val requestOptions = RequestOptions()
        requestOptions.placeholder(circularProgressDrawable)
        requestOptions.error(R.drawable.icon)
        requestOptions.skipMemoryCache(true)
        requestOptions.fitCenter()

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

            Glide.with(context).load(plantOrgansList.get(position).plantOrganUrl)
                .apply(requestOptions)
                .into(holder.organImage)
            holder.txtOrganName.setText(plantOrgansList.get(position).organName.toString())

            // deleting the plant Organ
            holder.deleteIcon.setOnClickListener {

                val dialog = Dialog(context)
                dialog.setContentView(R.layout.dialog_delete_message)
                dialog.setCanceledOnTouchOutside(false)
                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    dialog.dismiss()
                }

                dialog.findViewById<Button>(R.id.btnDelete).setOnClickListener {
                     // deleting data from Firestore
                    val db = Firebase.firestore
                    db.collection("Plant_Organ").get().addOnSuccessListener {
                            result ->
                        // deleting each document in collection of Plant_Organ
                        db.collection("Plant_Organ").document(result.documents.get(position).id).delete().addOnFailureListener {
                            AppPreferences.showToast(context, "Deleted Image Successfully! ")
                        }
                    }

                    // deleting data from Firebase Storage
                    val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(plantOrgansList.get(position).plantOrganUrl)
                    storageReference.delete().addOnSuccessListener {

                        plantOrgansList.removeAt(holder.adapterPosition)
                        notifyItemRemoved(holder.adapterPosition)
                        notifyItemRangeChanged(holder.adapterPosition, plantOrgansList.size)
                        holder.itemView.visibility = View.GONE
                        Toast.makeText(context, "Plant Organ Deleted Successfully !", Toast.LENGTH_LONG).show()

                    }.addOnFailureListener{
                        Toast.makeText(context, "Some Error Occurred. Please try again!!!", Toast.LENGTH_LONG).show()
                    }

                    dialog.dismiss()
                }

                dialog.show()


            }



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
        val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteIcon)

    }

}