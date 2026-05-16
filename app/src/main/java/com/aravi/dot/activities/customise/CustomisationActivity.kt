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
import android.os.Bundle
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

        preferenceManager = PreferenceManager(this)

        binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
        binding.iconsSwitch.setOnClickListener {
            preferenceManager.isIconsEnabled = !preferenceManager.isIconsEnabled
            binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
        }

        when (preferenceManager.dotPosition) {
            0 -> binding.dotAlignmentGroup.check(binding.alignLeft.id)
            1 -> binding.dotAlignmentGroup.check(binding.alignRight.id)
            2 -> binding.dotAlignmentGroup.check(binding.alignCenter.id)
        }

        binding.dotAlignmentGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (checkedId == binding.alignLeft.id && isChecked) preferenceManager.setDotPostion(0)
            if (checkedId == binding.alignRight.id && isChecked) preferenceManager.setDotPostion(1)
            if (checkedId == binding.alignCenter.id && isChecked) preferenceManager.setDotPostion(2)
        }

        binding.resetDefaults.setOnClickListener {
            preferenceManager.isIconsEnabled = true
            preferenceManager.setDotPostion(1)
            binding.iconsSwitch.checked(preferenceManager.isIconsEnabled)
            binding.dotAlignmentGroup.check(binding.alignRight.id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
