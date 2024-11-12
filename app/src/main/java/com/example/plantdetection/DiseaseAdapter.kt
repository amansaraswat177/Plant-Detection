package com.example.plantdetection

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class DiseaseAdapter(context: Context, private val diseaseList: List<Disease>) :
    ArrayAdapter<Disease>(context, android.R.layout.simple_list_item_1, diseaseList), Filterable {

    private var filteredList = diseaseList

    override fun getCount(): Int = filteredList.size
    override fun getItem(position: Int): Disease? = filteredList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val disease = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = disease?.name
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val results = if (query.isEmpty()) {
                    diseaseList
                } else {
                    diseaseList.filter { it.name.lowercase().contains(query) }
                }

                return FilterResults().apply { values = results }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Disease>
                notifyDataSetChanged()
            }
        }
    }
}
