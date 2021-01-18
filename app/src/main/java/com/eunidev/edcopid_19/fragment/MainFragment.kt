package com.eunidev.edcopid_19.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.eunidev.edcopid_19.IndonesiaProvince
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.api.APIInit
import com.eunidev.edcopid_19.model_data.CovidModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val FILE_NAME = "Saved_Province"
    private val KEY_NAME = "Province_Key"

    private lateinit var activity: Context

    private lateinit var spinner: Spinner
    private lateinit var tvPositif: TextView
    private lateinit var tvSembuh: TextView
    private lateinit var tvMeninggal: TextView
    private lateinit var tvPositifIndonesia: TextView
    private lateinit var tvSembuhIndonesia: TextView
    private lateinit var tvMeninggalIndonesia: TextView
    private lateinit var tvDirawatIndonesia: TextView
    private lateinit var provinceData: ArrayList<CovidModel.ParentCIP>
    private lateinit var indonesiaData: ArrayList<CovidModel.CovidIndonesia>
    private lateinit var fetchIndonesia: Job
    private lateinit var fetchProvince: Job

    override fun onAttach(context: Context) {
        this.activity = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        spinner = v.findViewById(R.id.spinnerProvince_MainFragment)
        tvPositif = v.findViewById(R.id.tvPositif_MainFragment)
        tvPositifIndonesia = v.findViewById(R.id.tvPositifIndonesia_MainFragment)
        tvSembuh = v.findViewById(R.id.tvSembuh_MainFragment)
        tvSembuhIndonesia = v.findViewById(R.id.tvSembuhIndonesia_MainFragment)
        tvMeninggal = v.findViewById(R.id.tvMeninggal_MainFragment)
        tvMeninggalIndonesia = v.findViewById(R.id.tvMeninggalIndonesia_MainFragment)
        tvDirawatIndonesia = v.findViewById(R.id.tvDirawatIndonesia_MainFragment)

        indonesiaData = ArrayList()
        provinceData = ArrayList()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchIndonesiaData()
        fetchProvinceData()
        val province = arrayOf("--Select Province--", IndonesiaProvince.ACEH.name, IndonesiaProvince.BALI.name, IndonesiaProvince.BANGKA_BELITUNG.name, IndonesiaProvince.BANTEN.name, IndonesiaProvince.BENGKULU.name, IndonesiaProvince.DAERAH_ISTIMEWA_YOGYAKARTA.name, IndonesiaProvince.DKI_JAKARTA.name, IndonesiaProvince.GORONTALO.name, IndonesiaProvince.JAMBI.name, IndonesiaProvince.JAWA_BARAT.name, IndonesiaProvince.JAWA_TENGAH.name, IndonesiaProvince.JAWA_TIMUR.name, IndonesiaProvince.KALIMANTAN_BARAT.name, IndonesiaProvince.KALIMANTAN_SELATAN.name, IndonesiaProvince.KALIMANTAN_TENGAH.name, IndonesiaProvince.KALIMANTAN_TIMUR.name, IndonesiaProvince.KALIMANTAN_UTARA.name, IndonesiaProvince.KEPULAUAN_RIAU.name, IndonesiaProvince.LAMPUNG.name, IndonesiaProvince.MALUKU.name, IndonesiaProvince.MALUKU_UTARA.name, IndonesiaProvince.NTB.name, IndonesiaProvince.NTT.name, IndonesiaProvince.PAPUA.name, IndonesiaProvince.PAPUA_BARAT.name, IndonesiaProvince.RIAU.name, IndonesiaProvince.SULAWESI_BARAT.name, IndonesiaProvince.SULAWESI_SELATAN.name, IndonesiaProvince.SULAWESI_TENGAH.name, IndonesiaProvince.SULAWESI_TENGGARA.name, IndonesiaProvince.SULAWESI_UTARA.name, IndonesiaProvince.SUMATERA_BARAT.name, IndonesiaProvince.SUMATERA_SELATAN.name, IndonesiaProvince.SUMATERA_UTARA.name)
        val spinnerAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, province)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = spinner.selectedItem.toString().toLowerCase(Locale.ROOT).replace("_".toRegex(), " ")
                if (selected == "--select province--") {
                    Toast.makeText(activity, "Select Province!", Toast.LENGTH_SHORT).show();
                } else {
                    provinceData.forEach {
                        if (it.attributes.province.toLowerCase(Locale.ROOT).replace("\\s".toRegex(), " ") == selected) {
                            tvSembuh.text = it.attributes.getWell.toString()
                            tvPositif.text = it.attributes.positive.toString()
                            tvMeninggal.text = it.attributes.death.toString()
                        }
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initIndonesiaData() {
        indonesiaData.forEach {
            tvPositifIndonesia.text = it.positive
            tvSembuhIndonesia.text = it.getWell
            tvMeninggalIndonesia.text = it.death
            tvDirawatIndonesia.text = it.dirawat
        }
    }

    private fun fetchIndonesiaData() {
        fetchIndonesia = lifecycleScope.launch {
            val covidAPI = APIInit.createApi()

            Log.i("Retrofit", "Indonesia: Starting Fetch Data")
            covidAPI.getIndonesiaData().enqueue(object : Callback<List<CovidModel.CovidIndonesia>> {
                override fun onResponse(call: Call<List<CovidModel.CovidIndonesia>>, response: Response<List<CovidModel.CovidIndonesia>>) {
                    Log.i("Retrofit", "Indonesia: onRespone()")
                    if (response.isSuccessful) {
                        Log.i("Retrofit", "Indonesia: Respone Successful")
                        response.body()?.forEach {
                            if (!indonesiaData.contains(it)) {
                                indonesiaData.add(it)
                                Log.i("retrofit", "added indonesiaData")
                                Log.i("retrofit", it.toString())
                            }
                        }
                        initIndonesiaData()
                    } else {
                        Toast.makeText(activity, "respone not successfull", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<CovidModel.CovidIndonesia>>, t: Throwable) {
                    Log.e("Retrofit", "Indonesia: ${t.message!!}")
                    Toast.makeText(activity, "Indonesia: ${t.message!!}", Toast.LENGTH_LONG).show()
                }
            })
        }
        
        fetchIndonesia.start()
    }

    private fun fetchProvinceData() {
        fetchProvince = lifecycleScope.launch {
            val covidAPI = APIInit.createApi()

            Log.i("Retrofit", "IProvince: Starting Fetch Data")
            covidAPI.getProvinceData().enqueue(object : Callback<List<CovidModel.ParentCIP>> {
                override fun onResponse(call: Call<List<CovidModel.ParentCIP>>, response: Response<List<CovidModel.ParentCIP>>) {
                    Log.i("Retrofit", "IProvince: onRespone()")
                    if (response.isSuccessful) {
                        Log.i("Retrofit", "IProvince Respone Successful")
                        response.body()?.forEach {
                            provinceData.add(it)
                        }
                    } else {
                        Toast.makeText(activity, "respone not successfull", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<CovidModel.ParentCIP>>, t: Throwable) {
                    Log.e("Retrofit", "Province: ${t.message!!}")
                    Toast.makeText(activity, "Province: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
        
        fetchProvince.start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}