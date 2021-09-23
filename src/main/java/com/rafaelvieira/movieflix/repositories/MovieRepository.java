package com.rafaelvieira.movieflix.repositories;

import com.rafaelvieira.movieflix.entities.Genre;
import com.rafaelvieira.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Long> {

    /*
    @Query("SELECT DISTINCT obj FROM Movie obj WHERE "
            + "(:genre IS NULL OR :genre = obj.genre) AND "
            + "(LOWER(obj.title) LIKE LOWER(CONCAT('%',:title,'%')))")
    Page<Movie> find(Genre genre,  Pageable pageable);
     */

    @Query("SELECT obj FROM Movie obj WHERE :genre IS NULL OR obj.genre = :genre")
    Page<Movie> find( Genre genre,  Pageable pageable);

    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.reviews WHERE obj IN :movies")
    List<Movie> findMoviesWithReviews(List<Movie> movies);
}
