package com.siele.worldnews.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.siele.worldnews.R

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}