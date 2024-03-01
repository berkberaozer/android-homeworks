package com.berkberaozer.hw2

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.TextUtils
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProvider
import com.berkberaozer.hw2.databinding.ActivityAddBinding
import java.text.ParseException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs


class AddActivity : AppCompatActivity() {
    private val dateFormat = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
    private val todayFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY")
    lateinit  var binding: ActivityAddBinding
    var gDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var showViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gDetector =  GestureDetectorCompat(this, CustomGesture())

        binding.btAdd.setOnClickListener {
            val date = binding.etDate.text.toString().trim()
            if (TextUtils.isEmpty(date) || !isValidDate(date))
                Toast.makeText(this, "Please enter a valid date (DD/MM/YYYY)", Toast.LENGTH_SHORT).show()
            else{
                showViewModel.addShow(Show(binding.etName.text.toString(), binding.sbScore.progress.toInt(), binding.etDate.text.toString(), binding.rgType.indexOfChild(binding.rgType.findViewById(binding.rgType.checkedRadioButtonId))))
                Toast.makeText(this, "Show added.", Toast.LENGTH_SHORT).show()

                binding.rgType.check(binding.radioButton4.id)
                binding.etName.setText("")
                binding.etDate.setText("")
                binding.sbScore.progress = 5
            }
        }

        binding.btToday.setOnClickListener {
            val currentDate = todayFormat.format(LocalDateTime.now())
            binding.etDate.setText(currentDate.toString())
        }
    }

    private fun isValidDate(date: String): Boolean {
        return try {
            dateFormat.isLenient = false
            dateFormat.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gDetector?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class CustomGesture: GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val distanceX = e2.x - e1!!.x
            val distanceY = e2.y - e1!!.y
            if (abs(distanceX) > abs(distanceY) && abs(distanceX) > 100 && abs(velocityX) > 100)
                if (distanceX > 0) {
                    finish()
                    return true
                }
            return false
        }
    }
}