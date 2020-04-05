package com.example.animals_app

import android.app.Application
import com.example.animals_app.dependency_injection.PrefsModule
import com.example.animals_app.util.SharedPreferencesHelper

class PrefsModuleTest(val mockPrefs: SharedPreferencesHelper) : PrefsModule() {
    override fun providesSharedPreferences(application: Application): SharedPreferencesHelper {
        return mockPrefs
    }
}