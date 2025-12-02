package com.example.lab_week_13

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_13.model.Movie
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import java.util.Calendar
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.lifecycle.repeatOnLifecycle

class MainActivity : AppCompatActivity() {
    private val movieAdapter by lazy {
        MovieAdapter(object : MovieAdapter.MovieClickListener {
            override fun onMovieClick(movie: Movie) {
                openMovieDetails(movie)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.movie_list)
        recyclerView.adapter = movieAdapter

        val movieRepository = (application as MovieApplication).movieRepository

        val movieViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MovieViewModel(movieRepository) as T
                }
            })[MovieViewModel::class.java]
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this
    }


        private fun openMovieDetails(movie: Movie) {
            val intent = Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsActivity.EXTRA_TITLE, movie.title)
                putExtra(DetailsActivity.EXTRA_RELEASE, movie.releaseDate)
                putExtra(DetailsActivity.EXTRA_OVERVIEW, movie.overview)
                putExtra(DetailsActivity.EXTRA_POSTER, movie.posterPath)
            }
            startActivity(intent)
        }
    }
