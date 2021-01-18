package com.eunidev.edcopid_19.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.eunidev.edcopid_19.MapsActivity
import com.eunidev.edcopid_19.R
import com.eunidev.edcopid_19.databinding.ActivityMainBinding
import com.eunidev.edcopid_19.fragment.GlobalDataFragment
import com.eunidev.edcopid_19.fragment.MainFragment
import com.eunidev.edcopid_19.model_data.CovidModel
import com.google.android.material.navigation.NavigationView
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

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

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.apiSource_Menu_Navview -> {
                    val url = "https://kawalcorona.com"
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
                }

                R.id.instagram_MenuNavview -> {
                    val url = "https://instagram.com/re.vel_?igshid=20sswd17ne9u"
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
                }

                R.id.github_MenuNavview -> {
                    val url = "https://github.com/kafri8889"
                    startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)))
                }

                R.id.reportbug_MenuNavview -> {
                    val i = Intent(Intent.ACTION_SEND)
                    i.putExtra(Intent.EXTRA_EMAIL, arrayOf("kafri8889@gmail.com", "kafri8889g4@gmail.com")) // Put Your Email Here
                    i.putExtra(Intent.EXTRA_SUBJECT, "Report Bug, App { Covid-19_AppExample }")
                    i.putExtra(Intent.EXTRA_TITLE, "")
                    i.type = "message/rfc822"
                    startActivity(Intent.createChooser(i, "Launch Email"))
                }

                R.id.globalData_MenuNavview -> {
                    changeFragment(GlobalDataFragment.newInstance("gd", "gd"))
                }

                R.id.dashBoard_MenuNavview -> {
                    changeFragment(MainFragment.newInstance("mf", "mf"))
                }

                R.id.settings_MenuNavview -> {
                    // Settings
                }

                R.id.exitApp_MenuNavview -> {
                    finish()
                    exitProcess(1)
                }

            }

            val dl = binding.drawerLayout
            dl.isSelected = false
            dl.closeDrawer(GravityCompat.START)

            return@setNavigationItemSelectedListener true
        }
    }

    private fun init() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        toolbar = binding.includeToolbbarMainActivity.toolbar
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        changeFragment(MainFragment.newInstance("mf", "mf"))
    }

    fun showInfoCountry(data: CovidModel.ParentCG) {
        val i = Intent(applicationContext, MapsActivity::class.java)
        i.putExtra("copidData", data)

        startActivity(i)
        finish()
    }

    fun changeFragment(f: Fragment) {
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout_MainActivity, f)
        fm.commit()
    }

}