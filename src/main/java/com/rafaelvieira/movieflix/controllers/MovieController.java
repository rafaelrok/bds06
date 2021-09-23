package com.rafaelvieira.movieflix.controllers;

import com.rafaelvieira.movieflix.dto.MoviesDTO;
import com.rafaelvieira.movieflix.dto.ReviewDTO;
import com.rafaelvieira.movieflix.services.MovieService;
import com.rafaelvieira.movieflix.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<MoviesDTO>> findAllPaged(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "title") String orderBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<MoviesDTO> list = service.findAllPaged( genreId,  pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MoviesDTO> findById(@PathVariable Long id){
        MoviesDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }


    @GetMapping(value = "{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> findReviews(@PathVariable Long id) {
        List<ReviewDTO> list = reviewService.findMovieReviews(id);
        return ResponseEntity.ok().body(list);
    }

}
