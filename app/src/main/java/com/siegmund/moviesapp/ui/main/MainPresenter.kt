package com.siegmund.moviesapp.ui.main

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.siegmund.moviesapp.api.Movie
import com.siegmund.moviesapp.api.MovieResponse
import com.siegmund.moviesapp.api.TMDBApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter: MvpBasePresenter<MainView>() {
    @Inject lateinit var api: TMDBApi
    private var pageCount = 0
    private var currentPage = 0

    fun onVisible() = loadMoviesForPage(page = 1)

    fun onInvisible() = Unit

    fun onCreditsClicked() = view?.openCreditsScreen()

    fun onItemClicked(movie: Movie, position: Int) = view?.openDetailsScreen(movie, position)

    fun onRefreshPulled() = view?.refreshItems()

    fun onLoadMore() = if (currentPage + 1 <= pageCount) {
        loadMoviesForPage(page = currentPage + 1)
    } else Unit

    private fun loadMoviesForPage(page: Int) = Thread {
        api.getPopularMovies(page = page).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse?>) {
                response.body()?.let {
                    currentPage = it.page
                    pageCount = it.totalPages
                    view?.addItems(it.results)
                }
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                view?.showErrorMessage()
            }
        })
    }.start()
}