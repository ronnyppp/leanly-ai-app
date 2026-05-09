package com.example.talkieai.models

import android.content.Context
import com.google.gson.Gson

object WeightStorage {

    private const val PREFS_NAME = "weights"
    private const val KEY = "weight_list"

    private val gson = Gson()

    // save weight list as Json in shared preferences
    fun saveWeights(context: Context, list: List<WeightEntry>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val json = gson.toJson(list)

        prefs.edit()
            .putString(KEY, json)
            .apply()
    }
    // load weight list
    fun loadWeights(context: Context): MutableList<WeightEntry> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val json = prefs.getString(KEY, null)

        return if (json != null) {
            gson.fromJson(json, Array<WeightEntry>::class.java).toMutableList()
        } else {
            mutableListOf()
        }
    }
}