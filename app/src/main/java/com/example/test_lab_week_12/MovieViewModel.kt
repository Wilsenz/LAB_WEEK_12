package com.example.test_lab_week_12

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
class MovieViewModel(private val movieRepository: MovieRepository)
    : ViewModel() {
    init {
        fetchPopularMovies()
    }

    private val _popularMovies = MutableStateFlow(
        emptyList<Movie>()
    )
    val popularMovies: StateFlow<List<Movie>> = _popularMovies

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error
    private fun fetchPopularMovies() {
        // Launch a coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
                .catch { e ->
                    // catch is a terminal operator that catches exceptions from the Flow
                    _error.value = "An exception occurred: ${e.message}"
                }
                .collect { movies ->
                    // LOGIKA ASSIGNMENT DIMULAI DI SINI

                    // 1. Dapatkan tahun saat ini (sama seperti logika Part 1)
                    val currentYear =
                        java.util.Calendar.getInstance().get(java.util.Calendar.YEAR).toString()

                    // 2. Lakukan filter dan sort pada list movies
                    val filteredMovies = movies
                        .filter { movie ->
                            // Filter agar hanya menampilkan film tahun ini (null safe)
                            movie.releaseDate?.startsWith(currentYear) == true
                        }
                        .sortedByDescending {
                            // Urutkan berdasarkan popularitas dari tinggi ke rendah
                            it.popularity
                        }

                    // 3. Masukkan hasil yang sudah difilter ke StateFlow
                    _popularMovies.value = filteredMovies
                }
        }
    }
}


