package com.example.realm_registration.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.realm_registration.R

class SpinnerAdapter(var davlatList:ArrayList<String>): BaseAdapter() {
    override fun getCount(): Int =davlatList.size

    override fun getItem(position: Int): String=davlatList[position]

    override fun getItemId(position: Int): Long =position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.spinner_item, parent, false)
        val textview = view.findViewById<TextView>(R.id.spinner_item_tv)
        textview.text=davlatList[position]
        return view
    }
}