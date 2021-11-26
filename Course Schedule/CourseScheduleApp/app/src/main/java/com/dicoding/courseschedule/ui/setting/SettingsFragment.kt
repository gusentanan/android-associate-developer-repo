package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        findPreference<ListPreference>(getString(R.string.pref_key_dark))?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                newValue?.let {
                    val modeSet = when ((it as String).toUpperCase(Locale.ROOT)) {
                        NightMode.ON.name -> NightMode.ON
                        NightMode.OFF.name -> NightMode.OFF
                        NightMode.AUTO.name -> NightMode.AUTO
                        else -> NightMode.AUTO
                    }
                    updateTheme(modeSet.value)
                }
                true
            }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        var dailyReminder: DailyReminder
        val preferenceNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        preferenceNotification?.setOnPreferenceChangeListener { _, newValue ->
            newValue?.let {
                dailyReminder = DailyReminder()
                when(it as Boolean){
                    true -> {
                        context?.let { reminder ->  dailyReminder.setDailyReminder(reminder) }
                    }
                    false -> {
                        context?.let { reminder -> dailyReminder.cancelAlarm(reminder) }
                    }
                }
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}