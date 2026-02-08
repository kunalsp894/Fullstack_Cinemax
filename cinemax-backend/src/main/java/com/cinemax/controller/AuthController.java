package com.cinemax.controller;

import com.cinemax.model.User;
import com.cinemax.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
		try {
			User saved = userService.register(user);
			saved.setPassword(null);
			return ResponseEntity.ok(saved);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("error", "Registration failed"));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
	    String email = payload.get("email");
	    String password = payload.get("password");

	    Optional<User> userOpt = userService.login(email, password);

	    if (userOpt.isPresent()) {
	        User u = userOpt.get();
	        u.setPassword(null);
	        return ResponseEntity.ok(u);
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("error", "Invalid email or password"));
	    }
	}

}
