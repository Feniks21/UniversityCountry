package com.example.country1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UniversityViewModel : ViewModel() {
    private val _universities = MutableLiveData<List<University>>()
    val universities: LiveData<List<University>> = _universities

    private val _countries = MutableLiveData<List<String>>()
    val countries: LiveData<List<String>> = _countries

    fun getUniversities(country: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getUniversities(country)
                if (response.isSuccessful) {
                    _universities.postValue(response.body())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getUniversities()
                if (response.isSuccessful) {
                    val universities = response.body() ?: emptyList()
                    val distinctCountries = universities.map { it.country }.distinct()
                    _countries.postValue(distinctCountries)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
