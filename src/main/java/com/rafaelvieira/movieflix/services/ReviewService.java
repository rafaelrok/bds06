package com.rafaelvieira.movieflix.services;

import com.rafaelvieira.movieflix.dto.MoviesDTO;
import com.rafaelvieira.movieflix.dto.ReviewDTO;
import com.rafaelvieira.movieflix.entities.Movie;
import com.rafaelvieira.movieflix.entities.Review;
import com.rafaelvieira.movieflix.entities.Role;
import com.rafaelvieira.movieflix.entities.User;
import com.rafaelvieira.movieflix.repositories.MovieRepository;
import com.rafaelvieira.movieflix.repositories.ReviewRepository;
import com.rafaelvieira.movieflix.services.exceptions.ForbiddenException;
import com.rafaelvieira.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AuthService authService;

/*
    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Movie movie = movieRepository.getOne(dto.getMovieId());
        User user = authService.authenticated();
        for (Role role : user.getRoles()) {
            if (role.getAuthority().equals("ROLE_VISITOR")) {
                    throw new ForbiddenException("Access denied");
            }
        }
        Review review = new Review(null, dto.getText(), user, movie);
        review = repository.save(review);
        return new ReviewDTO(review);
    }

 */
    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review entity = new Review();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ReviewDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findMovieReviews(Long id) throws ResourceNotFoundException {
        List<Review> list = repository.findMovieReviews(id);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Entity not found");
        }
        return list.stream().map(x -> new ReviewDTO(x, x.getUser())).collect(Collectors.toList());
    }

    private void copyDtoToEntity(ReviewDTO dto, Review entity) {
        Movie movie = movieRepository.getOne(dto.getMovieId());
        User user = authService.authenticated();
        authService.validateSelfOrMember(user.getId()); //Verifica se o usuario é realmente um membro

        entity.setText(dto.getText());
        entity.setMovie(movie);
        entity.setUser(user);
    }

}
