package com.cinemax.controller;

import com.cinemax.model.Booking;
import com.cinemax.model.Movie;
import com.cinemax.model.User;
import com.cinemax.repository.BookingRepository;
import com.cinemax.repository.MovieRepository;
import com.cinemax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@PostMapping
	public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
		Optional<User> uOpt = userRepository.findByEmail(request.getUserEmail());
		Optional<Movie> mOpt = movieRepository.findBySlug(request.getMovieId());

		if (uOpt.isEmpty() || mOpt.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid user or movie");
		}

		Movie movie = mOpt.get();
		String showTime = request.getShowTime();
		String[] requestedSeats = request.getSeats().split(",");

		// Fetch existing bookings for same movie & showtime
		List<Booking> existingBookings = bookingRepository.findByMovieAndShowTime(movie.getId(), showTime);

		// Collect all already booked seats
		List<String> bookedSeats = existingBookings.stream().flatMap(b -> List.of(b.getSeats().split(",")).stream())
				.map(String::trim).toList();

		// Check for conflicts
		for (String seat : requestedSeats) {
			if (bookedSeats.contains(seat.trim())) {
				return ResponseEntity.badRequest().body("Seat " + seat + " is already booked for this showtime!");
			}
		}

		// All clear — save booking
		Booking b = new Booking();
		b.setUser(uOpt.get());
		b.setMovie(movie);
		b.setSeats(request.getSeats());
		b.setShowTime(showTime);
		b.setTotal(request.getTotal());
		bookingRepository.save(b);

		return ResponseEntity.ok("Booking successful!");
	}

	@GetMapping("/booked-seats")
	public ResponseEntity<?> getBookedSeats(@RequestParam String movieSlug, @RequestParam String showTime) {

		Optional<Movie> movieOpt = movieRepository.findBySlug(movieSlug);
		if (movieOpt.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid movie");
		}

		List<Booking> bookings = bookingRepository.findByMovieAndShowTime(movieOpt.get().getId(), showTime);
		List<String> seats = bookings.stream().flatMap(b -> List.of(b.getSeats().split(",")).stream()).map(String::trim)
				.toList();

		return ResponseEntity.ok(seats);
	}

	static class BookingRequest {
		private String userEmail;
		private String movieId;
		private String seats;
		private String showTime;
		private double total;

		// getters and setters
		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}

		public String getMovieId() {
			return movieId;
		}

		public void setMovieId(String movieId) {
			this.movieId = movieId;
		}

		public String getSeats() {
			return seats;
		}

		public void setSeats(String seats) {
			this.seats = seats;
		}

		public String getShowTime() {
			return showTime;
		}

		public void setShowTime(String showTime) {
			this.showTime = showTime;
		}

		public double getTotal() {
			return total;
		}

		public void setTotal(double total) {
			this.total = total;
		}
	}
}