package com.eunidev.edcopid_19

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.eunidev.edcopid_19.model_data.CovidModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var cData: CovidModel.ParentCG
    private lateinit var tvCountryRegion: TextView
    private lateinit var tvLastUpdate: TextView
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvConfirmed: TextView
    private lateinit var tvRecovery: TextView
    private lateinit var tvDeaths: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        init()

        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val loc = LatLng(cData.attributes.latidude, cData.attributes.longitude)
        mMap.addMarker(MarkerOptions().position(loc).title("Marker in ${cData.attributes.countryRegion}"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        cData = intent.getSerializableExtra("copidData") as CovidModel.ParentCG

        tvCountryRegion = findViewById(R.id.tvCountryRegion_MapsActivity)
        tvLastUpdate = findViewById(R.id.tvLastUpdate_MapsActivity)
        tvLatitude = findViewById(R.id.tvLatitude_MapsActivity)
        tvLongitude = findViewById(R.id.tvLongitude_MapsActivity)
        tvConfirmed = findViewById(R.id.tvConfirmed_MapsActivity)
        tvRecovery = findViewById(R.id.tvRecovered_MapsActivity)
        tvDeaths = findViewById(R.id.tvDeaths_MapsActivity)

        tvCountryRegion.text = "Country Region: ${cData.attributes.countryRegion}"
        tvLastUpdate.text = "Last Update: ${cData.attributes.lastUpdateInMillis}"
        tvLatitude.text = "Latitude: ${cData.attributes.latidude}"
        tvLongitude.text = "Longitude: ${cData.attributes.longitude}"
        tvConfirmed.text = cData.attributes.confirmed.toString()
        tvRecovery.text = cData.attributes.recovered.toString()
        tvDeaths.text = cData.attributes.deaths.toString()
    }
}