package com.eunidev.edcopid_19.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.adapter.RecyclerviewGlobalDataAdapter
import com.eunidev.edcopid_19.api.APIInit
import com.eunidev.edcopid_19.model_data.CovidModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GlobalDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GlobalDataFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var activity: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var globalData: ArrayList<CovidModel.ParentCG>
    private lateinit var fetchGlobalData: Job

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_global_data, container, false)

        recyclerView = v.findViewById(R.id.recyclerView_GlobalDataFragment)

        globalData = ArrayList()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchGlobalData()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return false
            }
        })
    }

    private fun fetchGlobalData() {
        fetchGlobalData = lifecycleScope.launch {
            val covidAPI = APIInit.createApi()

            covidAPI.getGlobalData().enqueue(object : Callback<List<CovidModel.ParentCG>> {
                override fun onResponse(call: Call<List<CovidModel.ParentCG>>, response: Response<List<CovidModel.ParentCG>>) {
                    Log.i("Retrofit", "Global: onRespone()")
                    if (response.isSuccessful) {
                        Log.i("Retrofit", "Global: Respone Successful")
                        response.body()?.forEach {
                            globalData.add(it)
                        }

                        recyclerView.adapter = RecyclerviewGlobalDataAdapter(activity, globalData)
                    } else {
                        Toast.makeText(activity, "respone not successfull", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<CovidModel.ParentCG>>, t: Throwable) {
                    Log.e("Retrofit", "Global: ${t.message!!}")
                    Toast.makeText(activity, "Global: ${t.message!!}", Toast.LENGTH_LONG).show()
                }
            })
        }

        fetchGlobalData.start()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GlobalDataFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                GlobalDataFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}