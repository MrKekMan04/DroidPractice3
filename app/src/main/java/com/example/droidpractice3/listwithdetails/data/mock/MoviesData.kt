package com.example.droidpractice3.listwithdetails.data.mock

import com.example.droidpractice3.listwithdetails.domain.entity.MovieFullEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieShortEntity
import com.example.droidpractice3.listwithdetails.domain.entity.MovieType

object MoviesData {

    val moviesShort = listOf(
        MovieShortEntity(
            title = "Поднятие уровня в одиночку",
            year = "2024",
            id = "tt0083658",
            type = MovieType.ANIME,
            posterUrl = "https://anilibria.top/storage/releases/posters/9600/05RqjXuiUbRh54AQFRTwUuHoHYDaORCk.webp"
        ),
        MovieShortEntity(
            title = "Наруто Ураганные хроники",
            year = "2007",
            id = "tt1856101",
            type = MovieType.ANIME,
            posterUrl = "https://anilibria.top/storage/releases/posters/413/MH0zJJK7hdw7lzx1JRVb1Z3bEWuLdCDm.webp"
        ),
        MovieShortEntity(
            title = "Боруто: Новое поколение Наруто",
            year = "2017",
            id = "tt0083635",
            type = MovieType.ANIME,
            posterUrl = "https://anilibria.top/storage/releases/posters/3996/ZJHHMeMP0624ZTpWmc0j52brIzIY3wFE.webp"
        ),
        MovieShortEntity(
            title = "Карта детектива Такао Амэку",
            year = "2025",
            id = "tt1790864",
            type = MovieType.ANIME,
            posterUrl = "https://anilibria.top/storage/releases/posters/9876/PvPhMTfXZbXpZryREulxNGylptAo6aOY.webp"
        ),
        MovieShortEntity(
            title = "Re:Zero. Жизнь в другом мире с нуля 3",
            year = "2024",
            id = "tt1843101",
            type = MovieType.ANIME,
            posterUrl = "https://anilibria.top/storage/releases/posters/9783/I2rM5kEn12ItiNRYrVjK5Vj2eqmkbMUh.webp"
        )
    )

    val moviesFull = listOf(
        MovieFullEntity(
            title = "Поднятие уровня в одиночку",
            year = "2024",
            plot = "С момента внезапного появления \"врат\" — путей в другие миры — прошло уже больше десяти лет. За это время очень важной стала профессия \"охотников\" — людей, получивших сверхъестественные способности и зарабатывающих зачисткой подземелий внутри врат.",
            poster = "https://anilibria.top/storage/releases/posters/9600/05RqjXuiUbRh54AQFRTwUuHoHYDaORCk.webp",
            ratings = listOf(
                MovieFullEntity.Rating(
                    source = "AnimeGO",
                    value = "9.3/10"
                )
            ),
            imdbID = "tt0083658",
            type = MovieType.ANIME,
        ),
        MovieFullEntity(
            title = "Наруто Ураганные хроники",
            year = "2007",
            plot = "Приключения самого известного в мире шиноби продолжаются! Наруто идёт к своей мечте - стать хокаге, преодолевая трудности и попадая в передряги.",
            poster = "https://anilibria.top/storage/releases/posters/413/MH0zJJK7hdw7lzx1JRVb1Z3bEWuLdCDm.webp",
            ratings = listOf(
                MovieFullEntity.Rating(
                    source = "AnimeGO",
                    value = "9.3/10"
                )
            ),
            imdbID = "tt1856101",
            type = MovieType.ANIME,
        ),
        MovieFullEntity(
            title = "Боруто: Новое поколение Наруто",
            year = "2017",
            plot = "Коноха, она же «деревня скрытого листа» — это деревня ниндзя, в которой воспитываются настоящие воины, сильные духом и верные своему делу. Маленький мальчик по имени Наруто, будучи изгоем, мечтал добиться звания «хокаге», главы деревни.",
            poster = "https://anilibria.top/storage/releases/posters/3996/ZJHHMeMP0624ZTpWmc0j52brIzIY3wFE.webp",
            ratings = listOf(
                MovieFullEntity.Rating(
                    source = "AnimeGO",
                    value = "5.1/10"
                )
            ),
            imdbID = "tt1790864",
            type = MovieType.ANIME,
        )
    )
}