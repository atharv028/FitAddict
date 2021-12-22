package com.tare.fitaddict.recipes

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.tare.fitaddict.R
import kotlin.math.floor


class RecipeView : AppCompatActivity() {
    private lateinit var img : ImageView
    private lateinit var title : TextView
    private lateinit var serving : TextView
//    private lateinit var carbsPercent : TextView
    private lateinit var carbsWeight : TextView
//    private lateinit var fatPercent : TextView
    private lateinit var fatWeight : TextView
//    private lateinit var proteinPercent : TextView
    private lateinit var proteinWeight : TextView
    private lateinit var pieChart : PieChart
    private lateinit var pieData : PieData
    private var data : ArrayList<PieEntry> = ArrayList<PieEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_view)
        pieChart = findViewById(R.id.pieChart)
        img = findViewById(R.id.recipeImg)
        title = findViewById(R.id.TVtitleview)
        serving = findViewById(R.id.servingSize)
//        carbsPercent = findViewById(R.id.carbsPercent)
        carbsWeight = findViewById(R.id.carbsWeight)
//        fatPercent = findViewById(R.id.fatsPercent)
        fatWeight = findViewById(R.id.fatsWeight)
//        proteinPercent = findViewById(R.id.proteinPercent)
        proteinWeight = findViewById(R.id.proteinWeight)
        pieChart.setUsePercentValues(true)
        val nutri = intent.getStringArrayListExtra("nutrition")!!
        val url = intent.getStringExtra("imgUrl")
        title.text = intent.getStringExtra("name")
        serving.text = "Serving size: ${intent.getStringExtra("serving")?.toFloat()?.toInt()}"
        fatWeight.text = "${floor(nutri[0].toFloat()).toInt()} g"
        carbsWeight.text = "${floor(nutri[1].toFloat()).toInt()} g"
        proteinWeight.text = "${floor(nutri[2].toFloat()).toInt()} g"
        val sum = nutri[0].toFloat() + nutri[1].toFloat() + nutri[2].toFloat()
//        carbsPercent.text = (floor(nutri[1].toFloat() / sum) * 100).toInt().toString()
//        fatPercent.text = (floor(nutri[0].toFloat() / sum) * 100).toInt().toString()
//        proteinPercent.text = (floor(nutri[2].toFloat() / sum) * 100).toInt().toString()
        for (i in nutri)
        {
            Log.d("TAG", i.toString())
        }
        Glide.with(this).load(url).into(img)

        data.add(PieEntry(nutri[0].toFloat(), "Fat"))
        data.add(PieEntry(nutri[1].toFloat(), "Carbs"))
        data.add(PieEntry(nutri[2].toFloat(), "Protein"))
        val temp = PieDataSet(data, "")
        temp.colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
        pieChart.description = Description()
        temp.valueFormatter = PercentFormatter()
        pieData = PieData(temp)
        pieData.setValueTextSize(13f)
        pieChart.setDrawEntryLabels(false)
        pieChart.data = pieData
        pieChart.invalidate()
    }

}