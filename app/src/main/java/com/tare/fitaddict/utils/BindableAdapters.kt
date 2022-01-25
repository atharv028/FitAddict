package com.tare.fitaddict

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makeramen.roundedimageview.RoundedImageView
import com.tare.fitaddict.diary.FoodAdapter.BreakfastAdapter
import com.tare.fitaddict.diary.FoodAdapter.DinnerAdapter
import com.tare.fitaddict.diary.FoodAdapter.LunchAdapter
import com.tare.fitaddict.diary.FoodAdapter.SnackAdapter
import com.tare.fitaddict.home.HomeNewsAdapter
import com.tare.fitaddict.pojo.entities.*
import com.tare.fitaddict.search.FoodSearchViewModel
import com.tare.fitaddict.search.ResultAdapter
import com.tare.fitaddict.workout.WorkoutAdapter


@BindingAdapter("newsItemViewModel")
fun bindNewsItem(recyclerView: RecyclerView, itemList: List<Article>?) {
    val adapter = checkAdapterNews(recyclerView)
    adapter.update(itemList)
}

@BindingAdapter("loadFoodItemsBreakfast")
fun bindFoodItemBreakfast(recyclerView: RecyclerView, itemList: List<Food>?) {
    val adapter = checkAdapterFoodBreakfast(recyclerView)
    adapter.addAll(itemList)
    Log.d("TEST", "CALLED WITH $itemList")

}

@BindingAdapter("loadFoodItemsLunch")
fun bindFoodItemLunch(recyclerView: RecyclerView, itemList: List<Food>?) {
    val adapter = checkAdapterFoodLunch(recyclerView)
    adapter.addAll(itemList)
}

@BindingAdapter("loadFoodItemsDinner")
fun bindFoodItemDinner(recyclerView: RecyclerView, itemList: List<Food>?) {
    val adapter = checkAdapterFoodDinner(recyclerView)
    adapter.addAll(itemList)
}

@BindingAdapter("loadFoodItemsSnacks")
fun bindFoodItemSnacks(recyclerView: RecyclerView, itemList: List<Food>?) {
    val adapter = checkAdapterFoodSnack(recyclerView)
    adapter.addAll(itemList)
}

@BindingAdapter("loadWorkout")
fun bindWorkout(imageView: ImageView, rawUrl: String) {
    var url = rawUrl
    val temp = url.substring(4)
    url = "https$temp"
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("workoutItemViewModel")
fun bindWorkoutItem(recyclerView: RecyclerView, itemList: List<ResponseWorkoutItem>?) {
    val adapter = checkAdapterWorkout(recyclerView)
    adapter.update(itemList)
}

@BindingAdapter("fillEntries")
fun bindEntries(spinner: AppCompatSpinner, itemList: List<String>) {
    val adapter = ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, itemList)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
}

@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(
    pAppCompatSpinner: AppCompatSpinner,
    newSelectedValue: String?,
    newTextAttrChanged: InverseBindingListener
) {
    pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    if (newSelectedValue != null) {
        val pos = (pAppCompatSpinner.adapter as ArrayAdapter<String?>).getPosition(newSelectedValue)
        pAppCompatSpinner.setSelection(pos, true)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(pAppCompatSpinner: AppCompatSpinner): String {
    return pAppCompatSpinner.selectedItem as String
}

@BindingAdapter("fetchImage")
fun fetchImage(imageView: ImageView, url: String?) {
    if(url != null){
    Glide.with(imageView)
        .load(url)
        .into(imageView)
    }
}

private fun checkAdapterNews(recyclerView: RecyclerView): HomeNewsAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is HomeNewsAdapter) {
        recyclerView.adapter as HomeNewsAdapter
    } else {
        val homeNewsAdapter = HomeNewsAdapter()
        recyclerView.adapter = homeNewsAdapter
        homeNewsAdapter
    }
}

private fun checkAdapterFoodBreakfast(recyclerView: RecyclerView): BreakfastAdapter {
    return if (recyclerView.adapter is BreakfastAdapter && recyclerView.adapter != null) {
        recyclerView.adapter as BreakfastAdapter
    } else {
        val adapter = BreakfastAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}

private fun checkAdapterFoodLunch(recyclerView: RecyclerView): LunchAdapter {
    return if (recyclerView.adapter is LunchAdapter && recyclerView.adapter != null) {
        recyclerView.adapter as LunchAdapter
    } else {
        val adapter = LunchAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}

private fun checkAdapterFoodDinner(recyclerView: RecyclerView): DinnerAdapter {
    return if (recyclerView.adapter is DinnerAdapter && recyclerView.adapter != null) {
        recyclerView.adapter as DinnerAdapter
    } else {
        val adapter = DinnerAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}

private fun checkAdapterFoodSnack(recyclerView: RecyclerView): SnackAdapter {
    return if (recyclerView.adapter is SnackAdapter && recyclerView.adapter != null) {
        recyclerView.adapter as SnackAdapter
    } else {
        val adapter = SnackAdapter()
        recyclerView.adapter = adapter
        adapter
    }
}





private fun checkAdapterWorkout(recyclerView: RecyclerView): WorkoutAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is WorkoutAdapter) {
        recyclerView.adapter as WorkoutAdapter
    } else {
        val adap = WorkoutAdapter()
        recyclerView.adapter = adap
        adap
    }
}


