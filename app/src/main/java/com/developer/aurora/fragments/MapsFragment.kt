package com.developer.aurora.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.developer.aurora.models.BusLocation

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapsFragment : Fragment() {

    private lateinit var reference: DatabaseReference
    private var locationIs: BusLocation? = null
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        reference.child("BusLocation").child("B2")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    googleMap.clear()
                    locationIs = snapshot.getValue(BusLocation::class.java)

                    val latitude = locationIs!!.latitude?.toDouble()
                    val longitude = locationIs!!.longitude?.toDouble()
                    val time = locationIs!!.time?.toString()
                    updateData(latitude, longitude, time, googleMap)

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Log.d("error", error.message.toString())

                }

            })


    }

    private fun updateData(
        latitude: Double?,
        longitude: Double?,
        time: String?,
        googleMap: GoogleMap
    ) {
        val sydney = LatLng(latitude!!, longitude!!)


        googleMap.addMarker(
            MarkerOptions().position(sydney).title(time!!).icon(
                BitmapDescriptorFactory.fromResource(com.developer.aurora.R.drawable.bus)
            )
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f))

//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(com.developer.aurora.R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reference = FirebaseDatabase.getInstance().reference
        val mapFragment =
            childFragmentManager.findFragmentById(com.developer.aurora.R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }

}