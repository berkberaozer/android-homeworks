package com.berkberaozer.hw1

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.berkberaozer.hw1.classes.School

class CustomSpinnerAdapter(var contextt: Context, var spinnerItemValues: ArrayList<School>)
    : ArrayAdapter<School>(contextt, R.layout.spinner_item, spinnerItemValues)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?,parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = contextt.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflator.inflate(R.layout.spinner_item, parent, false)

        val constraintLayout:ConstraintLayout = view.findViewById(R.id.itemConstraintLayout)
        val imgItemSchool:ImageView = view.findViewById(R.id.imgItemSchool)
        val tvItemSocialName: TextView = view.findViewById(R.id.tvItemGradingSystem)

        val selectedSchool = spinnerItemValues.get(position)
        tvItemSocialName.text = selectedSchool.getGradingSystem()

        val selectedGradingSystem = spinnerItemValues.get(position)
        imgItemSchool.setImageResource(selectedGradingSystem.getImgId())

        if (position % 2 == 0)
            constraintLayout.setBackgroundColor(Color.CYAN)
        else
            constraintLayout.setBackgroundColor(Color.YELLOW)
        return view
    }
}
