package com.example.country1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UniversityAdapter(
    private var universities: List<University>,
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>(), Filterable {

    private var filteredUniversities: List<University> = universities

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return UniversityViewHolder(view)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
        holder.bind(filteredUniversities[position])
    }

    override fun getItemCount(): Int = filteredUniversities.size

    inner class UniversityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                onClickListener(filteredUniversities[adapterPosition].web_pages?.get(0) ?: "")
            }
        }

        fun bind(university: University) {
            textView.text = university.name
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.toLowerCase()
                filteredUniversities = if (query.isNullOrEmpty()) {
                    universities
                } else {
                    universities.filter { it.name.toLowerCase().contains(query) }
                }
                return FilterResults().apply {
                    values = filteredUniversities
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredUniversities = results?.values as List<University>
                notifyDataSetChanged()
            }
        }
    }
}
