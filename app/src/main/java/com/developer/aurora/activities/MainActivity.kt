package com.developer.aurora.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.developer.aurora.R
import com.developer.aurora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val sharedPrefFile = "loadProfile"
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setContentView(binding.root)
        sharedPreferences= getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        navController = findNavController(R.id.home_frag_container)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu
        binding.bottomNavigationView.setupWithNavController(menu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.mapsFragment ||destination.id == R.id.busSelectionFragment ||destination.id == R.id.resultsWebFragment || destination.id == R.id.attendanceFragment || destination.id == R.id.uploadIdeaFragment || destination.id == R.id.examResultsFragment || destination.id == R.id.placementsFragment || destination.id == R.id.eventDetailFragment || destination.id == R.id.addFragment || destination.id == R.id.editNoteFragment || destination.id == R.id.lecturerListFragment || destination.id == R.id.lecturerDetailFragment || destination.id == R.id.ideaMenuScreenFragment) {

                binding.bottomNavigationView.visibility = View.GONE
            } else {


                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }


}