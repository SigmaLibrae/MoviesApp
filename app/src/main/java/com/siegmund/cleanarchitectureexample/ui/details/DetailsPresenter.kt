package com.siegmund.cleanarchitectureexample.ui.details

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.siegmund.cleanarchitectureexample.api.Movie

class DetailsPresenter(private val movie: Movie): MvpBasePresenter<DetailsView>() {
    fun onVisible() {
        view?.configureUI(
                url = movie.posterPath,
                title = movie.title,
                subtitle = movie.releaseDate,
                description = movie.overview
        )
    }

    fun onInvisible() = Unit
}