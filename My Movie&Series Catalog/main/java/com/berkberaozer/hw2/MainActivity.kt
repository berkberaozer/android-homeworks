package com.berkberaozer.hw2

import android.app.Dialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.indices
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkberaozer.hw2.Constants.MOVIE
import com.berkberaozer.hw2.Constants.SERIES
import com.berkberaozer.hw2.databinding.ActivityMainBinding
import com.berkberaozer.hw2.databinding.DialogBinding
import java.text.ParseException
import java.util.Collections
import java.util.Locale
import kotlin.math.abs

class MainActivity : AppCompatActivity(), CustomRecyclerViewAdapter.RecyclerAdapterInterface {
    lateinit  var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    private val dateFormat = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

    lateinit var adapter: CustomRecyclerViewAdapter
    private lateinit var showViewModel:ShowViewModel
    var gDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gDetector =  GestureDetectorCompat(this, CustomGesture())

        layoutManager = LinearLayoutManager(this)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        adapter = CustomRecyclerViewAdapter(this, ArrayList<Show>())
        binding.recyclerShow.setLayoutManager(layoutManager)
        binding.recyclerShow.setAdapter(adapter)

        showViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)
        getData()

        Thread {
            if(showViewModel.getCount() < 1) {
                prepareData()
            }
        }.start()

        binding.btDeleteAll.setOnClickListener {
            showViewModel.deleteAllShows()
            Toast.makeText(this, "Shows deleted.", Toast.LENGTH_SHORT).show()
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
                if (distanceX < 0) {
                    val intent = Intent(this@MainActivity, AddActivity::class.java)
                    startActivity(intent)
                    return true
                }
            return false
        }
    }

    fun getData() {
        showViewModel.readAllData.observe(this, Observer { shows ->
            adapter.setData(shows)
        })
    }

    private fun prepareData() {
        var shows=ArrayList<Show>()
        Collections.addAll(shows,
            Show("Interstellar", 9, "14/12/2023", MOVIE),
            Show("Inception", 6, "14/12/2023", MOVIE),
            Show("South Park", 3, "14/12/2023", SERIES))
        showViewModel.addShows(shows)
    }

    fun editDialog(show: Show) {
        val dialog = Dialog(this)
        val dialogBinding = DialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.etEditName.setText(show.name)
        dialogBinding.etEditDate.setText(show.date)
        dialogBinding.etEditScore.progress = show.score
        var id:Int;
        if(show.type == MOVIE)
            id = dialogBinding.radioButton.id
        else
           id = dialogBinding.radioButton2.id

        dialogBinding.rgEditType.check(id)

        dialogBinding.btnDialogClose.setOnClickListener {
            show.name = dialogBinding.etEditName.text.toString()
            show.date = dialogBinding.etEditDate.text.toString()
            show.score = dialogBinding.etEditScore.progress
            show.type = dialogBinding.rgEditType.indexOfChild(dialogBinding.rgEditType.findViewById(dialogBinding.rgEditType.checkedRadioButtonId))
            val date = show.date
            if (TextUtils.isEmpty(date) || !isValidDate(date)) {
                Toast.makeText(this, "Please enter a valid date (DD/MM/YYYY)", Toast.LENGTH_SHORT).show()
            }
            else {
                showViewModel.updateShow(show)

                Toast.makeText(this, "Show saved.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialogBinding.btEditDelete.setOnClickListener {
            showViewModel.deleteShow(show)
            Toast.makeText(this, "Show deleted.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun editItem(sc: Show) {
        editDialog(sc)
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
}