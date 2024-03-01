package com.berkberaozer.hw1

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.berkberaozer.cumulative.CumulativeActivity
import com.berkberaozer.hw1.classes.Course
import com.berkberaozer.hw1.databinding.ActivityMainBinding
import java.util.Collections
import kotlin.math.roundToLong

class MainActivity : AppCompatActivity() {
    lateinit var rgGradingSystem: RadioGroup
    lateinit var btOk: Button
    lateinit var customDialog: Dialog
    lateinit var gradingOne: ArrayList<String>
    lateinit var gradingTwo: ArrayList<String>
    lateinit var gradings: ArrayList<ArrayList<String>>
    private var gradingIndex: Int = 0
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: ArrayAdapter<String>
    lateinit var courses: ArrayList<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareData()
        createDialog()
        customDialog!!.show()

        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gradings[gradingIndex])
        binding.spinnerGrade.setAdapter(adapter)

        val colorFrom = Color.RED;
        val colorTo = Color.TRANSPARENT;
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.addUpdateListener { valueAnimator ->
            val color = valueAnimator.animatedValue as Int
            binding.tvTitle.setTextColor(color)
        }
        colorAnimation.duration = 1000
        colorAnimation.repeatCount = ValueAnimator.INFINITE
        colorAnimation.repeatMode = ValueAnimator.REVERSE

        colorAnimation.start();

        binding.sbCredit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.tvCredit.setText(
                    """$progress""".trimIndent()
                )
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.spinnerGrade.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected( parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val count = binding.spinnerGrade.count
                Log.d("asd", "$position")
                if(position > count / 3 * 2)
                    binding.imageView.setImageResource(R.drawable.sad)
                else if (position > count / 5 * 2.5)
                    binding.imageView.setImageResource(R.drawable.smile)
                else
                    binding.imageView.setImageResource(R.drawable.yay)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.btAddCourse.setOnClickListener {
            lateinit var course:Course
            if(binding.etCourseName.text.contentEquals(""))
                course = Course(binding.sbCredit.progress, binding.spinnerGrade.selectedItemPosition)
            else
                course = Course(binding.etCourseName.text.toString(), binding.sbCredit.progress, binding.spinnerGrade.selectedItemPosition)

            var flag = true
            for (c in courses)
                if(c.getName().equals(course.getName()))
                    flag = false
            if(!flag)
                displayToast("You added a course twice. Click to the Clear button if you didn't intended this.")
            courses.add(course)
            Course.courseCount++

            var result:String = ""
            for(course in courses)
                result += "Course Name: ${course.getName()}\nCourse Credit: ${course.getCredit()}\nCourse Grade: ${gradings[gradingIndex][course.getGradeId()]}\n\n"
            binding.tvScroll.setText(result)
        }

        binding.btCalculate.setOnClickListener{
            val switchActivityIntent: Intent = Intent(this, CumulativeActivity::class.java)
            val b = Bundle()

            b.putParcelableArrayList("courses", courses)
            b.putInt("gradingIndex", gradingIndex)
            switchActivityIntent.putExtras(b)
            CumulativeActivityResultLauncher.launch(switchActivityIntent)
        }

        binding.btClear.setOnClickListener {
            binding.tvScroll.setText("")
            courses.clear()
            binding.etCourseName.setText("")
            binding.sbCredit.progress = 0
            Course.courseCount = 0
        }

        binding.btSettings.setOnClickListener {
            val switchActivityIntent: Intent = Intent(this, SettingsActivity::class.java)
            val b = Bundle()
            b.putStringArrayList("gradingOne", gradingOne)
            b.putStringArrayList("gradingTwo", gradingTwo)
            b.putInt("gradingIndex", gradingIndex)

            switchActivityIntent.putExtras(b)
            SettingsActivityResultLauncher.launch(switchActivityIntent)
        }
    }

    var SettingsActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()) { result  ->

        if (result.resultCode == RESULT_OK) {
            val data = result.data
            gradingIndex = data!!.getIntExtra("gradingIndex", 0)
            adapter.clear()
            prepareData()
            adapter.addAll(gradings[gradingIndex])
            binding.tvScroll.setText("")
            courses.clear()
        }
        else
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
    }

    var CumulativeActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()) { result  ->

        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val gpa = data!!.getDoubleExtra("gpa", 0.0)
            makeAndShowDialogBox(gpa)
        }
        else
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
    }

    private fun makeAndShowDialogBox(gpa: Double) {
        val myDialogBox = AlertDialog.Builder(this)
        // set message, title, and icon
        myDialogBox.setTitle("Your (C)GPA is Calculated")
        myDialogBox.setMessage("Your (C)GPA is %.2f".format(gpa))
        if(gpa > 3)
            myDialogBox.setIcon(R.drawable.yay)
        else if(gpa > 2)
            myDialogBox.setIcon(R.drawable.smile)
        else
            myDialogBox.setIcon((R.drawable.sad))
        // Set three option buttons
        myDialogBox.setPositiveButton(
            "Close"
        ) { dialog, whichButton ->
            // whatever should be done when answering "YES" goes
            // here
        }
        myDialogBox.create()
        myDialogBox.show()
    }

    private fun displayToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun createDialog() {
        customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog)
        rgGradingSystem = customDialog!!.findViewById(R.id.rgGradingSystem)
        btOk = customDialog!!.findViewById(R.id.btOk)

        rgGradingSystem?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rbAA) {
                gradingIndex = 1
            } else {
                gradingIndex = 0
            }
            prepareData()
            adapter.clear()
            adapter.addAll(gradings[gradingIndex])
        })

        btOk.setOnClickListener(View.OnClickListener {
            customDialog!!.dismiss() //
        })
    }

    fun prepareData() {
        gradingOne = ArrayList()
        Collections.addAll(gradingOne, "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F", "FX", "FZ")
        gradingTwo = ArrayList()
        Collections.addAll(gradingTwo, "AA", "BA", "BB", "CB", "CC", "DC", "DD", "FD",	"FF", "NA")
        gradings = ArrayList()
        Collections.addAll(gradings, gradingOne, gradingTwo)
        courses = ArrayList()
    }
}