package com.rafaelvieira.movieflix.services;

import com.rafaelvieira.movieflix.dto.MoviesDTO;
import com.rafaelvieira.movieflix.entities.Genre;
import com.rafaelvieira.movieflix.entities.Movie;
import com.rafaelvieira.movieflix.repositories.GenreRepository;
import com.rafaelvieira.movieflix.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;


    @Transactional(readOnly = true)
    public Page<MoviesDTO> findAllPaged( Long genreId,  PageRequest pagerequest) {
        Genre genre = (genreId == 0) ? null : genreRepository.getOne(genreId);
        Page<Movie> list = repository.find( genre,  pagerequest);
        return list.map(x -> new MoviesDTO(x));
    }

    @PreAuthorize("hasAnyRole('MEMBER','VISITOR')")
    @Transactional(readOnly = true)
    public MoviesDTO findById(Long id) {
        Optional<Movie> obj = repository.findById(id);
        Movie entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new MoviesDTO(entity, entity.getReviews());
    }

}
