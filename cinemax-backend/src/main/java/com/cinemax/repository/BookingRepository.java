package com.cinemax.repository;

import com.cinemax.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	@Query("SELECT b FROM Booking b WHERE b.movie.id = :movieId AND b.showTime = :showTime")
    List<Booking> findByMovieAndShowTime(Long movieId, String showTime);
}