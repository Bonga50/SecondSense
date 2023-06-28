package com.example.resecondsense_v01

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resecondsense_v01.databinding.FragmentHomeBinding

//This is the home page fragment
class Home : Fragment(),RVAdapter_RecentEnty.OnItemClickListener,IminMaxUpdate {
   //binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RVAdapter_RecentEnty
    private var valuesUpdateListener: IminMaxUpdate? = null
    var dbhelper = DataContext

    private lateinit var txtMinValue: TextView
    private lateinit var txtMaxValue: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        //View binding

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = view.findViewById(R.id.recentRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewAdapter = RVAdapter_RecentEnty(dbhelper.getRecentEntry())

        recyclerViewAdapter.itemClickListener = this
        recyclerView.adapter = recyclerViewAdapter

        txtMinValue = view.findViewById(R.id.txtMinValue)
        txtMaxValue = view.findViewById(R.id.txtMaxValue)

        val btnCreatEntry : Button = view.findViewById(R.id.btnCreateEntry)
        btnCreatEntry.setOnClickListener {

            // Create an Intent to navigate to the target activity
            val intent = Intent(requireContext(), AddNewEntries::class.java)

            // Optionally, add extras to the Intent
            intent.putExtra("key", "value")

            // Start the activity
            startActivity(intent)
        }

        //Progress bar for min and max values
        binding.txtMinValue.text = dbhelper.min.toString()
        binding.txtMaxValue.text = dbhelper.min.toString()
        val loadingBar = view.findViewById<ProgressBar>(R.id.minMaxLoadingBar)

        val minValue = 2
        val maxValue = 100
        val progressValue = 80

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadingBar.min = minValue
        }

        loadingBar.max = maxValue
        loadingBar.progress = progressValue


        return view



    }
    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            // Check if the result code matches the one set by the other activity
            if (resultCode == Activity.RESULT_OK) {
                // Check if the intent and its extras are not null
                if (data != null && data.hasExtra("DATA_ENTRIES")) {
                    // Get the updated list of categories from the intent using the same key as before
                    val newData = dbhelper.getRecentEntry()
                    // Pass the updated list of categories to the adapter of the RecyclerView
                    (recyclerView.adapter as? RVAdapter_RecentEnty)?.updateData(newData)
                    // Notify the adapter that the data set has changed
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onItemClick(itemId: String) {
        val intent = Intent(requireContext(), EntryDetails::class.java)
        intent.putExtra("EntryId", itemId)
        startActivityForResult(intent, 1)
    }

    override fun onValuesUpdated() {
        view?.findViewById<TextView>(R.id.txtMinValue)?.text = dbhelper.min.toString()
        view?.findViewById<TextView>(R.id.txtMaxValue)?.text = dbhelper.max.toString()
    }



}