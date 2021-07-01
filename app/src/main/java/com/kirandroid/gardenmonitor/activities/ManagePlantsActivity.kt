package com.kirandroid.gardenmonitor.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.adapters.PlantOrganImageAdapter
import com.kirandroid.gardenmonitor.models.PlantOrganImageData
import com.kirandroid.gardenmonitor.utils.AppPreferences
import com.kirandroid.gardenmonitor.utils.AppUtils
import com.kirandroid.gardenmonitor.viewmodels.PlantOrganImageViewModel
import kotlinx.android.synthetic.main.activity_manage_plants.*
import org.w3c.dom.Text
import java.math.RoundingMode
import kotlin.math.roundToInt


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

    lateinit var db: FirebaseFirestore
    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference

    // viewmodel initialising
    lateinit var plantOrganImageViewModel: PlantOrganImageViewModel

    lateinit var imagesList: ArrayList<String>
    lateinit var organsList: ArrayList<String>

    lateinit var uploadingLottieAnimLayout: LinearLayout
    lateinit var txtUploadingPercentage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_plants)

        // viewmodel
        plantOrganImageViewModel = ViewModelProvider(this)[PlantOrganImageViewModel::class.java]

        // initialising App Preferences
        AppPreferences.init(this)

        imagesList = ArrayList()
        organsList = ArrayList()

        val intent = intent
        if (!intent.getStringExtra("image_source")!!.isEmpty()) {
            // intent is passed show dialog
            showCustomDialog(intent.getStringExtra("imageURI")!!)
        }

        // initialising Cloud Firestore
        FirebaseApp.initializeApp(this)
        db = Firebase.firestore
        storage = Firebase.storage
        storageRef = storage.reference

        recognizePlant.setOnClickListener { recognisePlant() }


    }


    @SuppressLint("CutPasteId", "SetTextI18n")
    fun showCustomDialog(stringURIExtra: String) {

        val selectedImageURI: Uri = Uri.parse(stringURIExtra)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_show_selected_image)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        //Animation Related
        uploadingLottieAnimLayout = dialog.findViewById(R.id.uploadingAnimLayout)
        txtUploadingPercentage = dialog.findViewById(R.id.txtUploadingPercentage)


        // setting the Image
        dialog.findViewById<ImageView>(R.id.selectedImageView).setImageURI(selectedImageURI)

        dialog.findViewById<ChipGroup>(R.id.chipGroup)
            .setOnCheckedChangeListener { group, checkedId ->
                //  Toast.makeText(this, checkedId, Toast.LENGTH_LONG).show()
            }

        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            getPlantOrganImageData()
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnUpload).setOnClickListener {

            //retrieving the selected chip
            val checkedChipId = dialog.findViewById<ChipGroup>(R.id.chipGroup).checkedChipId // Returns View.NO_ID if singleSelection = false


            // checking whether the user selected one chip
            if (!checkedChipId.equals(View.NO_ID)) {

                val chip = dialog.findViewById<ChipGroup>(R.id.chipGroup).findViewById<Chip>(checkedChipId)

                // Uploading File to Storage

                // creating folder with Current Date and Time
                val imagesRef: StorageReference? = storageRef.child("${AppUtils.getCurrentDate()}/${chip.text.toString()+" "+AppUtils.getCurrentTime()}")
                val uploadTask = imagesRef!!.putFile(selectedImageURI)
                var imageUrl = ""
                uploadTask.addOnSuccessListener { taskSnapshot ->
                    // file size
                    val size = taskSnapshot.bytesTransferred
                }.addOnProgressListener {
                    val progress = (100.0* it.bytesTransferred) / it.totalByteCount

                    // disabling the chiplayout, txtSelectChip and Button Layout
                    dialog.findViewById<TextView>(R.id.txtOrgan).visibility = View.GONE
                    dialog.findViewById<ChipGroup>(R.id.chipGroup).visibility = View.GONE
                    dialog.findViewById<LinearLayout>(R.id.buttonLayout).visibility = View.GONE

                    // show loadinganimLayout
                    uploadingLottieAnimLayout.visibility = View.VISIBLE
                    txtUploadingPercentage.text = progress.roundToInt().toString() + "%"


                    Log.d("TAG", "Upload is $progress% done")
                }.continueWithTask {
                    if (!it.isSuccessful) {
                        it.exception?.let {
                            throw it
                            AppPreferences.showToast(this,it.localizedMessage)
                        }
                    }
                    imagesRef.downloadUrl
                }.addOnCompleteListener{
                    if(it.isSuccessful) {
                        //val downloadUrl = it.result
                        imageUrl = it.result.toString()

                        // sending data to Cloud Firestore
                        val plantOrganImage = PlantOrganImageData(imageUrl.toString(), chip.text.toString(), AppUtils.getCurrentDate() + " " + AppUtils.getCurrentTime())
                        db.collection("Plant_Organ")
                            .add(plantOrganImage)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(this, "Image Uploaded Successfully !", Toast.LENGTH_LONG).show()
                            }.addOnFailureListener { error ->
                                Toast.makeText(this, "Error adding data: $error", Toast.LENGTH_LONG).show()
                            }
                        getPlantOrganImageData()
                        dialog.dismiss()
                    } else {
                        getPlantOrganImageData()
                        Toast.makeText(this,"Some Error Occurred! Please try again", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                // user has not selected any chip
                Toast.makeText(this, "Please choose relevant Organ", Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }

    fun getPlantOrganImageData() {

        val plantsOrganList: ArrayList<PlantOrganImageData> = ArrayList<PlantOrganImageData>()
        db.collection("Plant_Organ").get().addOnSuccessListener { result ->

                // reading data from firestore
                for (document in result) {
                    val plantOrganImageData = document.toObject<PlantOrganImageData>()
                    plantsOrganList.add(plantOrganImageData)
                    imagesList.add(plantOrganImageData.plantOrganUrl)
                    organsList.add(plantOrganImageData.organName.lowercase())

                    Log.d("TAG", "${document.id} => ${document.data}")
                }

                // Adding placeholder layout
                if (plantsOrganList.size < 5) {
                    plantsOrganList.add(PlantOrganImageData("imageURL", "addImage", "Date"))
                }

                // disabling and enabling the button
                if (!plantsOrganList.isEmpty() && plantsOrganList.size < 4) {
                    recognizePlant.isEnabled = false
                }

                // initialising the adapter
                if (!plantsOrganList.isEmpty()) {
                    val plantOrganImageAdapter = PlantOrganImageAdapter(this, plantsOrganList)
                    val gridLayoutManager = GridLayoutManager(this, 2)
                    plantsRecycler.adapter = plantOrganImageAdapter
                    plantsRecycler.layoutManager = gridLayoutManager
                } else {
                    Toast.makeText(this, "" + plantsOrganList.size, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }

    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    private fun recognisePlant() {
        // showing Dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_recognise_plant)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.findViewById<TextView>(R.id.txtRecognisingStatus).text = "Searching 391,000+ Species all over the World..."

        if(!imagesList.isEmpty() && !organsList.isEmpty()) {

            plantOrganImageViewModel.getPlantIdentificationData(imagesList,organsList).observe(this, Observer {

                dialog.findViewById<TextView>(R.id.txtRecognisingStatus).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.txtMessageAfterRecognised).visibility = View.VISIBLE

                dialog.findViewById<LottieAnimationView>(R.id.recognisingAnim).setAnimation(R.raw.success_anim)
                dialog.findViewById<LottieAnimationView>(R.id.recognisingAnim).playAnimation()
                dialog.findViewById<LottieAnimationView>(R.id.recognisingAnim).loop(false)


                dialog.findViewById<TextView>(R.id.txtMessageAfterRecognised).text = spannableString("Recognised as " + it.results[0].species.scientificName + " with " + "%.2f".format(it.results[0].score.toFloat()*100) + "% accuracy",14,(14+it.results[0].species.scientificName.length))

                /* for(result in it.results) {
                    // displaying all lists
                }
*/
               // AppPreferences.showToast(this, it.results[0].species.scientificName + " is recognised with Confidence Score of " + it.results[0].score)


                //txtPlantName.text = it.results[0].species.scientificName + " is recognised with Confidence Score of " + it.results[0].score

            })

            dialog.findViewById<Button>(R.id.btnAddPlant).setOnClickListener { startActivity(Intent(this,MainActivity::class.java)) }

            dialog.show()

        } else {
            AppPreferences.showToast(this,"Something wrong Occurred! Please try again")
        }

    }

    private fun spannableString(text: String, start: Int, end: Int): SpannableString? {
        val spannableString = SpannableString(text)
       // val redColor = ColorStateList(arrayOf(intArrayOf()), intArrayOf(-0x25645E))
        val greenColor = ColorStateList.valueOf(resources.getColor(R.color.greenVar2))
        val highlightSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, greenColor, null)
        spannableString.setSpan(highlightSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        //spannableString.setSpan(BackgroundColorSpan(-0x300b8), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }



}