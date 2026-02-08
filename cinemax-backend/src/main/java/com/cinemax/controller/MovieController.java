package com.cinemax.controller;

import com.cinemax.model.Movie;
import com.cinemax.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{slug}")
    public Movie getMovieBySlug(@PathVariable String slug) {
        return movieRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Movie not found: " + slug));
    }
}