package com.eunidev.edcopid_19

import android.content.Context
import android.content.SharedPreferences

class PrefrencesManager {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
        private var INSTANCE: PrefrencesManager? = null

        fun getInstance(context: Context): PrefrencesManager? {
            if (INSTANCE == null) {
                synchronized(PrefrencesManager::class.java) {
                    INSTANCE = PrefrencesManager()
                }
            }

            return INSTANCE
        }
    }
}