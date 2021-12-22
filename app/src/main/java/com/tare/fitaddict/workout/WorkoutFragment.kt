package com.tare.fitaddict.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonArrayRequest
import com.bumptech.glide.Glide
import com.tare.fitaddict.MySingleton
import com.tare.fitaddict.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkoutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkoutFragment : Fragment(), Clicked {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isImageFitToScreen : Boolean = false
    private lateinit var spinner: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var image : ImageView
    private var mAdapter : Adapter = Adapter(this)
    val array = arrayListOf("Chest", "Back", "Shoulders", "Cardio", "Arms", "Legs", "Neck", "Waist")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout, container, false)
        val spinnerAdapter = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_spinner_item, array)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = view.findViewById(R.id.SpinWorkout)
        recyclerView = view.findViewById(R.id.workoutRV)
        image = view.findViewById(R.id.IVexercise)
        spinner.apply {
            adapter = spinnerAdapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selected = array[position]
                selected = selected.lowercase()
                if(selected == "arms")
                {
                    fetchData("upper%20arms")
                }
                else if(selected == "legs")
                {
                    fetchData("upper%20legs")
                }
                else
                    fetchData(selected)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("TAG", "Please Select")
            }

        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
        return view
    }

    private fun fetchData(category : String)
    {
        Log.d("TAG", "Called with $category")
        val url = "https://exercisedb.p.rapidapi.com/exercises/bodyPart/$category"
        val jsonArrayRequest = object : JsonArrayRequest(
            Method.GET, url , null,
            {
                val listWorkout = ArrayList<exer>()
                for(i in 0 until it.length())
                {
                    Log.d("TAG", "Updating Workout")
                    val jsonObject = it.getJSONObject(i)
                    val data = exer(jsonObject.getString("equipment"),jsonObject.getString("gifUrl")
                    ,jsonObject.getString("name"),jsonObject.getString("target"))
                    listWorkout.add(data)
                }
                listWorkout.shuffle()
                mAdapter.update(listWorkout)
            },
            {
                Log.w("TAG", "Failed to update workout", it.cause)
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-rapidapi-host"] = "exercisedb.p.rapidapi.com"
                headers["x-rapidapi-key"] = "e4dae4e0a2msh9a8df3b86af5de1p1dcc95jsna5f9853ab7e0"
                return headers
            }
        }
        MySingleton.getInstance(activity?.applicationContext!!).addToRequestQueue(jsonArrayRequest)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkoutFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkoutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(item: exer) {
        Log.d("TAG", "CLICKED ON THE IMAGE")
        if(isImageFitToScreen)
        {
            isImageFitToScreen = false
            image.visibility = View.GONE
        }
        else
        {
            isImageFitToScreen = true
            image.bringToFront()
            image.visibility = View.VISIBLE
            var url = item.gifUrl
            val temp = url.substring(4)
            url = "https$temp"
            Glide.with(activity?.applicationContext!!).load(url).into(image)
            image.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
            image.adjustViewBounds = true
        }
    }
}