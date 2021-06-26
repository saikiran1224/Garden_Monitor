package com.kirandroid.gardenmonitor.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kirandroid.gardenmonitor.R
import com.kirandroid.gardenmonitor.fragments.MyGardenFragment
import com.kirandroid.gardenmonitor.fragments.ProfileFragment
import com.kirandroid.gardenmonitor.fragments.WaterNowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val myGardenFragment: MyGardenFragment = MyGardenFragment()
    private val waterNowFragment: WaterNowFragment = WaterNowFragment()
    private val profileFragment: ProfileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(myGardenFragment)

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_myGardenIcon -> {
                    replaceFragment(myGardenFragment)
                    true
                }
                R.id.nav_waterNowIcon -> {
                    replaceFragment(waterNowFragment)
                    true
                }
                R.id.nav_profileIcon -> {
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    // This method is used to replace Fragment
    private fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer,fragment)
        transaction.commit()
    }
}