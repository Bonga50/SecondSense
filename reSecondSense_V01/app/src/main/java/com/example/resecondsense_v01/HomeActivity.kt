package com.example.resecondsense_v01

import android.app.AlertDialog
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.example.resecondsense_v01.databinding.ActivityHomePageBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.util.Date
import com.example.resecondsense_v01.AnalyticsFragment

//This class holds the fragments
class HomeActivity :  AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    val currentDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)



        // code fo creating the Tablayout
        // video for this https://youtu.be/DJiqBhqyZeg
        var tabLayout: TabLayout = findViewById(R.id.tabNavigation)
        var viewPage : ViewPager = findViewById(R.id.vpNavigation)
        // Create an instance of your custom adapter
        val vpAdapter = VPAdapter(supportFragmentManager)
        // Add more fragments as needed
        vpAdapter.addFragment(Home(),"HOME")
        vpAdapter.addFragment(Category(),"CATEGORY")
        vpAdapter.addFragment(Entries(),"ENTRIES")
        vpAdapter.addFragment(AnalyticsFragment(),"ANALYTICS")
        // Set the adapter to the ViewPager
        viewPage.adapter = vpAdapter
        tabLayout.setupWithViewPager(viewPage)


        //navigation drawer
        val btnopenDrawer : Button = findViewById(R.id.btnMenuDrawer)
        val navView : NavigationView = findViewById(R.id.navDrawerView)
        drawerLayout = findViewById(R.id.drawerLayout)
        btnopenDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)

            navView.setNavigationItemSelectedListener(this)

        }

        //navigation from activities
        if (intent.hasExtra("DATA")) {
            val desiredFragmentIndex = intent.getIntExtra("DATA", 1)
            viewPage.currentItem = desiredFragmentIndex
        }

        if (intent.hasExtra("DATA_ENTRIES")) {
            val desiredFragmentIndex = intent.getIntExtra("DATA_ENTRIES", 3)
            viewPage.currentItem = desiredFragmentIndex
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navSetUserGoals -> {
                val popupView = layoutInflater.inflate(R.layout.popoup_min_max, null)
                val dialogBuilder = AlertDialog.Builder(this)
                    .setView(popupView)
                val cancelButton: Button = popupView.findViewById(R.id.CnButton)
                val doneButton: Button = popupView.findViewById(R.id.DoButton)
                val minEditText: EditText = popupView.findViewById(R.id.InputMin)
                val maxEditText: EditText = popupView.findViewById(R.id.InputMax)
                val dialog = dialogBuilder.create()

                val MaxView: TextView = popupView.findViewById(R.id.MinMaxTitle)
                dialog.show()
                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }

                doneButton.setOnClickListener{
                    // Retrieve the minimum and maximum values from the input fields


                    val minValue = minEditText.text.toString().toIntOrNull()
                    val maxValue = maxEditText.text.toString().toIntOrNull()

                    if (minValue != null && maxValue != null) {
                        // Check if both values are valid integers
                        if (minValue <= maxValue) {
                            // Valid input values

                            // Do whatever you want with the minimum and maximum values here
                            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Invalid input, show an error or handle it accordingly", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        Toast.makeText(this, "Invalid input, show an error or handle it accordingly", Toast.LENGTH_SHORT).show()
                    }
                }
                showMinMax()


            }

            R.id.nav_logout -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

        }
        return true
    }

    private fun showMinMax(){

    }



}