package com.tare.fitaddict.nutrition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore
import com.tare.fitaddict.R
private const val ARG_PARAM_LIST = "list"
private const val ARG_PARAM = "REM"
class Calories : Fragment() {
    private var list : ArrayList<String> = ArrayList()
    private lateinit var db : FirebaseFirestore
    private lateinit var pieChart : PieChart
    private lateinit var goal : TextView
    private lateinit var consumed : TextView
    private lateinit var pieData : PieData
    private var data : ArrayList<PieEntry> = ArrayList()
    private var remaining : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getStringArrayList(ARG_PARAM_LIST) as ArrayList<String>
            remaining = it.getStringArrayList(ARG_PARAM) as ArrayList<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_calories, container, false)
        db = FirebaseFirestore.getInstance()
        pieChart = v.findViewById(R.id.chartNutri)
        pieChart.setUsePercentValues(true)
        goal = v.findViewById(R.id.nurtriGoal)
        consumed = v.findViewById(R.id.nutriTotalCal)
        data.add(PieEntry(list[0].split(" ")[0].toFloat(), "Breakfast"))
        data.add(PieEntry(list[1].split(" ")[0].toFloat(), "Lunch"))
        data.add(PieEntry(list[2].split(" ")[0].toFloat(), "Dinner"))
        data.add(PieEntry(list[3].split(" ")[0].toFloat(), "Snacks"))
        val temp = PieDataSet(data, "")
        temp.colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
        pieChart.description = Description()
        temp.valueFormatter = PercentFormatter()
        pieData = PieData(temp)
        pieData.setValueTextSize(13f)
        pieChart.setDrawEntryLabels(false)
        pieChart.data = pieData
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Caloric Breakdown"
        pieChart.invalidate()

        goal.text = remaining[1]
        consumed.text = remaining[0]
        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        data.clear()
    }

    companion object{
        @JvmStatic
        fun newInstance(item: ArrayList<String>, remaining : ArrayList<String>) =
            Calories().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ARG_PARAM_LIST, item)
                    putStringArrayList(ARG_PARAM, remaining)
                }
            }
    }
}