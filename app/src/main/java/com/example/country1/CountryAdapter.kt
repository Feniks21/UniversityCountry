package com.example.country1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private var countries: List<String>,
    private val onClickListener: (String) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), Filterable {

    private var filteredCountries: List<String> = countries

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(filteredCountries[position])
    }

    override fun getItemCount(): Int = filteredCountries.size

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        init {
            itemView.setOnClickListener {
                onClickListener(filteredCountries[adapterPosition])
            }
        }

        fun bind(country: String) {
            textView.text = country
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()
                filteredCountries = if (query.isNullOrEmpty()) {
                    countries
                } else {
                    countries.filter { it.lowercase().contains(query) }
                }
                return FilterResults().apply {
                    values = filteredCountries
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCountries = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }

    fun updateCountries(newCountries: List<String>) {
        countries = newCountries
        filteredCountries = newCountries
        notifyDataSetChanged()
    }
}
