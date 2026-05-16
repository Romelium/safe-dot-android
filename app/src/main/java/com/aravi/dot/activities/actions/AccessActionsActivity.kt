package com.aravi.dot.activities.actions

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aravi.dot.activities.ignore.WhitelistActivity
import com.aravi.dot.database.AppDatabase
import com.aravi.dot.databinding.ActivityAccessActionsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.aravi.commons.base.BaseActivity
import org.koin.android.ext.android.inject

class AccessActionsActivity : BaseActivity() {

    private lateinit var binding: ActivityAccessActionsBinding
    private val appDatabase: AppDatabase by inject()

    override val contentView: View
        get() {
            binding = ActivityAccessActionsBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent?) {
        setSupportActionBar(binding.toolbar)
        setBack()

        val preferenceManager = com.aravi.dot.manager.PreferenceManager(this)

        binding.cameraSwitch.checked(preferenceManager.isCameraEnabled)
        binding.cameraSwitch.setOnClickListener {
            preferenceManager.isCameraEnabled = !preferenceManager.isCameraEnabled
            binding.cameraSwitch.checked(preferenceManager.isCameraEnabled)
        }

        binding.microphoneSwitch.checked(preferenceManager.isMicEnabled)
        binding.microphoneSwitch.setOnClickListener {
            preferenceManager.isMicEnabled = !preferenceManager.isMicEnabled
            binding.microphoneSwitch.checked(preferenceManager.isMicEnabled)
        }

        binding.locationSwitch.checked(preferenceManager.isLocationEnabled)
        binding.locationSwitch.setOnClickListener {
            preferenceManager.isLocationEnabled = !preferenceManager.isLocationEnabled
            binding.locationSwitch.checked(preferenceManager.isLocationEnabled)
        }

        binding.excludedApps.setOnClickListener {
            startActivity(Intent(this, WhitelistActivity::class.java))
        }

        binding.btnExcludedApps.setOnClickListener {
            startActivity(Intent(this, WhitelistActivity::class.java))
        }

        binding.btnOptions.setOnClickListener {
            Toast.makeText(this, "Options clicked", Toast.LENGTH_SHORT).show()
        }

        binding.clearAccessLogs.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Clear Access Logs")
                .setMessage("Are you sure you want to clear all access logs? This cannot be undone.")
                .setPositiveButton("Clear") { _, _ ->
                    appDatabase.logsDao().clearLogs()
                    Snackbar.make(binding.root, "Logs cleared", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Just toggle switch visually for now as we don't have a preference for it
        binding.timeSwitch.setOnClickListener {
            binding.timeSwitch.checked(!binding.timeSwitch.isChecked())
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
