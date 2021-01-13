package com.eunidev.edcopid_19.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.api.APIClient
import com.eunidev.edcopid_19.databinding.ActivityMainBinding
import com.eunidev.edcopid_19.fragment.MainFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var covidAPI: APIClient
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.instagram_MenuNavview -> {

                }
            }

            return@setNavigationItemSelectedListener true
        }

//        lifecycleScope.launch {
//            covidAPI = APIInit.createApi()
//
//            covidAPI.get("indonesia").enqueue(object : Callback<List<CovidModel.CovidIndonesia>> {
//                override fun onResponse(call: Call<List<CovidModel.CovidIndonesia>>, response: Response<List<CovidModel.CovidIndonesia>>) {
//                    if (response == null) {
//                        Toast.makeText(applicationContext, "respone is null (no data)", Toast.LENGTH_LONG).show()
//                    } else {
//                        val covidIndonesia = response.body()!!
//                    }
//                }
//
//                override fun onFailure(call: Call<List<CovidModel.CovidIndonesia>>, t: Throwable) {
//                    Log.e("Retrofit", t.message!!)
//                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                }
//            })
//        }
    }

    private fun init() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        toolbar = binding.includeToolbbarMainActivity.toolbar
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        changeFragment(MainFragment.newInstance("s", "s"))
    }

    fun changeFragment(f: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout_MainActivity, f)
        fm.commit()
    }

}