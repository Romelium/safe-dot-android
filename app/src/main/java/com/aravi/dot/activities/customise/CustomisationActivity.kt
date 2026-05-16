/*
 * Copyright (C) 2021.  Aravind Chowdary (@kamaravichow)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aravi.dot.activities.customise

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.aravi.dot.R
import com.aravi.dot.databinding.ActivityCustomisationBinding
import com.aravi.dot.manager.PreferenceManager

class CustomisationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomisationBinding
    private lateinit var preferenceManager: PreferenceManager

    private val updatedSomething = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomisationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        preferenceManager = PreferenceManager(this)

        binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
        updatePreviewIcons(preferenceManager.isIconsEnabled)

        binding.iconsSwitch.setOnClickListener {
            preferenceManager.isIconsEnabled = !preferenceManager.isIconsEnabled
            binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
            updatePreviewIcons(preferenceManager.isIconsEnabled)
        }

        when (preferenceManager.dotPosition) {
            0 -> binding.dotAlignmentGroup.check(binding.alignLeft.id)
            1 -> binding.dotAlignmentGroup.check(binding.alignRight.id)
            2 -> binding.dotAlignmentGroup.check(binding.alignCenter.id)
        }
        updatePreviewAlignment(preferenceManager.dotPosition)

        binding.dotAlignmentGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                if (checkedId == binding.alignLeft.id) {
                    preferenceManager.setDotPostion(0)
                    updatePreviewAlignment(0)
                }
                if (checkedId == binding.alignRight.id) {
                    preferenceManager.setDotPostion(1)
                    updatePreviewAlignment(1)
                }
                if (checkedId == binding.alignCenter.id) {
                    preferenceManager.setDotPostion(2)
                    updatePreviewAlignment(2)
                }
            }
        }

        updatePreviewColors()

        binding.cameraColor.setOnClickListener {
            val colorPicker = com.pes.androidmaterialcolorpickerdialog.ColorPicker(this,
                Color.red(preferenceManager.cameraDotColor), 
                Color.green(preferenceManager.cameraDotColor), 
                Color.blue(preferenceManager.cameraDotColor))
            colorPicker.show()
            colorPicker.enableAutoClose()
            colorPicker.setCallback { color: Int ->
                preferenceManager.cameraDotColor = color
                updatePreviewColors()
            }
        }

        binding.micColor.setOnClickListener {
            val colorPicker = com.pes.androidmaterialcolorpickerdialog.ColorPicker(this,
                Color.red(preferenceManager.micDotColor), 
                Color.green(preferenceManager.micDotColor), 
                Color.blue(preferenceManager.micDotColor))
            colorPicker.show()
            colorPicker.enableAutoClose()
            colorPicker.setCallback { color: Int ->
                preferenceManager.micDotColor = color
                updatePreviewColors()
            }
        }

        binding.locColor.setOnClickListener {
            val colorPicker = com.pes.androidmaterialcolorpickerdialog.ColorPicker(this,
                Color.red(preferenceManager.locationDotColor), 
                Color.green(preferenceManager.locationDotColor), 
                Color.blue(preferenceManager.locationDotColor))
            colorPicker.show()
            colorPicker.enableAutoClose()
            colorPicker.setCallback { color: Int ->
                preferenceManager.locationDotColor = color
                updatePreviewColors()
            }
        }

        binding.resetDefaults.setOnClickListener {
            preferenceManager.isIconsEnabled = true
            preferenceManager.setDotPostion(1)
            binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
            binding.dotAlignmentGroup.check(binding.alignRight.id)
            updatePreviewAlignment(1)
            updatePreviewIcons(true)
        }
    }

    private fun updatePreviewAlignment(position: Int) {
        val params = binding.dotHolder.layoutParams as RelativeLayout.LayoutParams
        params.removeRule(RelativeLayout.ALIGN_PARENT_START)
        params.removeRule(RelativeLayout.ALIGN_PARENT_END)
        params.removeRule(RelativeLayout.CENTER_HORIZONTAL)

        val marginEdge = (45 * resources.displayMetrics.density).toInt()

        when (position) {
            0 -> { // Left
                params.addRule(RelativeLayout.ALIGN_PARENT_START)
                params.marginStart = marginEdge
                params.marginEnd = 0
            }
            1 -> { // Right
                params.addRule(RelativeLayout.ALIGN_PARENT_END)
                params.marginStart = 0
                params.marginEnd = marginEdge
            }
            2 -> { // Center
                params.addRule(RelativeLayout.CENTER_HORIZONTAL)
                params.marginStart = 0
                params.marginEnd = 0
            }
        }
        binding.dotHolder.layoutParams = params
    }

    private fun updatePreviewIcons(showIcons: Boolean) {
        if (showIcons) {
            binding.dotCamera.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_camera))
            binding.dotMic.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_mic))
            binding.dotLocation.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_location))
        } else {
            binding.dotCamera.setImageDrawable(null)
            binding.dotMic.setImageDrawable(null)
            binding.dotLocation.setImageDrawable(null)
        }
    }

    private fun updatePreviewColors() {
        setViewTint(binding.dotCamera, preferenceManager.cameraDotColor)
        setViewTint(binding.dotMic, preferenceManager.micDotColor)
        setViewTint(binding.dotLocation, preferenceManager.locationDotColor)
    }

    private fun setViewTint(imageView: ImageView, color: Int) {
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_dot)?.mutate()
        drawable?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
        imageView.background = drawable
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
