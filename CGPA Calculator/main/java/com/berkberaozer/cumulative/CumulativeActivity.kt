package com.berkberaozer.cumulative

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.berkberaozer.hw1.R
import com.berkberaozer.hw1.classes.Course

class CumulativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var creditSum:Int = 0
        var scoreSum:Double = 0.0
        super.onCreate(savedInstanceState)

        val rintent = intent
        val courses = rintent.getParcelableArrayListExtra("courses", Course::class.java)
        val gradingIndex = rintent.getIntExtra("gradingIndex", 0)

        Log.d("CALCULATION", "$courses")

        if (courses != null)
            if(gradingIndex == 0)
                for(course in courses) {
                    val gradeId = course.getGradeId()
                    val credit = course.getCredit()
                    creditSum += credit

                    when(gradeId) {
                        0 -> scoreSum += 4 * credit
                        1 -> scoreSum += 4 * credit
                        2 -> scoreSum += 3.7 * credit
                        3 -> scoreSum += 3.3 * credit
                        4 -> scoreSum += 3 * credit
                        5 -> scoreSum += 2.7 * credit
                        6 -> scoreSum += 2.3 * credit
                        7 -> scoreSum += 2 * credit
                        8 -> scoreSum += 1.7 * credit
                        9 -> scoreSum += 1.3 * credit
                        10 -> scoreSum += 1 * credit
                        11 -> scoreSum += 0 * credit
                        12 -> scoreSum += 0 * credit
                        13 -> scoreSum += 0 * credit
                    }
                }
            else
                for(course in courses) {
                    val gradeId = course.getGradeId()
                    val credit = course.getCredit()
                    creditSum += credit
                    scoreSum += (4 - gradeId * 0.5) * credit
                    if(4 - gradeId * 0.5 < 0)
                        scoreSum += 0.5
                }

        val intent = Intent()
        val b = Bundle()
        val gpa = scoreSum / creditSum

        b.putDouble("gpa", gpa)
        intent.putExtras(b)

        setResult(RESULT_OK, intent)
        finish()
    }
}