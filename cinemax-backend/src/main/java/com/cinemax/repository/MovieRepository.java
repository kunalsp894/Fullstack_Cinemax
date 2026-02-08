package com.cinemax.repository;

import com.cinemax.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findBySlug(String slug);
}
