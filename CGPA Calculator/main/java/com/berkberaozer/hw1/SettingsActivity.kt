package com.berkberaozer.hw1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.berkberaozer.hw1.classes.School
import com.berkberaozer.hw1.databinding.ActivityMainBinding
import com.berkberaozer.hw1.databinding.ActivitySettingsBinding
import java.util.Collections

class SettingsActivity : AppCompatActivity() {
    lateinit var schoolItems:ArrayList<School>
    lateinit var binding: ActivitySettingsBinding
    lateinit var adapter: CustomSpinnerAdapter
    lateinit var gradingOne: ArrayList<String>
    lateinit var gradingTwo: ArrayList<String>
    var gradingIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rintent = intent.extras
        gradingOne = rintent?.getStringArrayList("gradingOne")!!
        gradingTwo = rintent?.getStringArrayList("gradingTwo")!!
        gradingIndex = rintent?.getInt("gradingIndex")!!
        prepareSpinner(gradingOne, gradingTwo)

        adapter = CustomSpinnerAdapter(this, schoolItems)
        binding.spinner.setAdapter(adapter)
        binding.spinner.setSelection(gradingIndex)

        binding.tvBack.setOnClickListener {
            val intent = Intent()
            val b = Bundle()
            b.putInt("gradingIndex", binding.spinner.selectedItemPosition)
            intent.putExtras(b)

            setResult(RESULT_OK, intent)

            finish()
        }
    }

    fun prepareSpinner(gradingOne:ArrayList<String>, gradingTwo:ArrayList<String>) {
        schoolItems = ArrayList()
        Collections.addAll<School>(
            schoolItems, School(gradingOne.subList(0, 3).joinToString(separator = "/"), R.drawable.bilkent), School(gradingTwo.subList(0, 3).joinToString(separator = "/"), R.drawable.metu)
        )
    }
}