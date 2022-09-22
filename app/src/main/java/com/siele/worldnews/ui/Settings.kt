package com.siele.worldnews.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceGroupAdapter
import androidx.preference.PreferenceScreen
import androidx.preference.PreferenceViewHolder
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.siele.worldnews.R

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
    @SuppressLint("RestrictedApi")
    override fun onCreateAdapter(preferenceScreen: PreferenceScreen): RecyclerView.Adapter<*> {
        return object :PreferenceGroupAdapter(preferenceScreen){
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): PreferenceViewHolder {
                val holder = super.onCreateViewHolder(parent, viewType)
                val customLayout = holder.itemView
                if (customLayout.id == R.id.settingsLayout){
                    val toolbar = holder.itemView.findViewById<MaterialToolbar>(R.id.settingsToolbar)
                    toolbar.apply {
                        title = getString(R.string.settings_label)
                        setupWithNavController(findNavController())
                    }
                }
                return holder
            }
        }
    }
}