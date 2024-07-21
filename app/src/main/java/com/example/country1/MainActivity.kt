package com.example.country1

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: CountryAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: UniversityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        setupRecyclerView(emptyList())

        viewModel.countries.observe(this) { countries ->
            setupRecyclerView(countries)
            checkLastStateAndOpen()
        }

        fetchCountries()
    }

    private fun fetchCountries() {
        viewModel.getCountries()
    }

    private fun setupRecyclerView(countries: List<String>) {
        adapter = CountryAdapter(countries) { country ->
            saveLastCountry(country)
            openUniversityActivity(country)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun saveLastCountry(country: String) {
        sharedPreferences.edit().putString("LAST_COUNTRY", country).apply()
    }

    private fun openUniversityActivity(country: String) {
        val intent = Intent(this, UniversityActivity::class.java)
        intent.putExtra("COUNTRY", country)
        startActivity(intent)
    }

    private fun checkLastStateAndOpen() {
        val lastCountry = sharedPreferences.getString("LAST_COUNTRY", null)
        if (lastCountry != null) {
            sharedPreferences.edit().remove("LAST_COUNTRY").apply()
            openUniversityActivity(lastCountry)
        }
    }
}
