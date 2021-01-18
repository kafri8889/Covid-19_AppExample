package com.eunidev.edcopid_19.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.model_data.CovidModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val ARG_CDATA_KEY = "cdata_key"

/**
 * A simple [Fragment] subclass.
 * Use the [MapsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var cData: CovidModel.ParentCG? = null

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var tvCountryRegion: TextView
    private lateinit var tvLastUpdate: TextView
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvConfirmed: TextView
    private lateinit var tvRecovery: TextView
    private lateinit var tvDeaths: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cData = it.getSerializable(ARG_CDATA_KEY) as CovidModel.ParentCG
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_maps, container, false)

        tvCountryRegion = v.findViewById(R.id.tvCountryRegion_MapsActivity)
        tvLastUpdate = v.findViewById(R.id.tvLastUpdate_MapsActivity)
        tvLatitude = v.findViewById(R.id.tvLatitude_MapsActivity)
        tvLongitude = v.findViewById(R.id.tvLongitude_MapsActivity)
        tvConfirmed = v.findViewById(R.id.tvConfirmed_MapsActivity)
        tvRecovery = v.findViewById(R.id.tvRecovered_MapsActivity)
        tvDeaths = v.findViewById(R.id.tvDeaths_MapsActivity)

        if (cData != null) {
            tvCountryRegion.text = cData!!.attributes.countryRegion
            tvLastUpdate.text = cData!!.attributes.lastUpdateInMillis.toString()
            tvLatitude.text = cData!!.attributes.latidude.toString()
            tvLongitude.text = cData!!.attributes.longitude.toString()
            tvConfirmed.text = cData!!.attributes.confirmed.toString()
            tvRecovery.text = cData!!.attributes.recovered.toString()
            tvDeaths.text = cData!!.attributes.deaths.toString()
        }

        mapFragment = v.findViewById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(cdata: CovidModel.ParentCG) =
                MapsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_CDATA_KEY, cdata)
                    }
                }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
        }

        // Add a marker in Sydney and move the camera
        val loc = cData?.attributes?.let {
            LatLng(it.latidude, cData!!.attributes.longitude)
        }

        mMap.addMarker(loc?.let {
            MarkerOptions().position(it).title("Marker in ${cData?.attributes?.countryRegion}")
        })

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
    }
}