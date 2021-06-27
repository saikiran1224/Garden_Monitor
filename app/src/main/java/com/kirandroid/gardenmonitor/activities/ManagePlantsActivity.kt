package com.kirandroid.gardenmonitor.activities

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kirandroid.gardenmonitor.R

/*
This Activity does the following:

 [Step 1]
 - Once user captures or selects picture from Gallery, shows Dialog consisting:
     * shows selected Image
     * gives Options to delete or modify image [CROP]
     * asks type of image [ leaf, flower, etc. ]
     * upload button to Upload Picture

 [Step 2]
 - Firebase Storage will consist of image uploaded along with category and will display all images here
 - Shows `+` Button to add new pictures repeats the same process again

 [Step 3]
 - Once user uploads atleast 3 images, RECOGNISE PLANT Button will send request to Server
 */

class ManagePlantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_plants)

        val intent = intent
        if(!intent.getStringExtra("image_source")!!.isEmpty()) {
            // intent is passed show dialog
            showCustomDialog(intent.getStringExtra("imageURI")!!)


        }






    }

    private fun showCustomDialog(stringURIExtra: String) {

        val selectedImageURI: Uri = Uri.parse(stringURIExtra)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_show_selected_image)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.findViewById<ImageView>(R.id.selectedImageView).setImageURI(selectedImageURI)


        dialog.show()



    }
}