package movie.app.controller;

import movie.app.entity.MovieInfo;
import movie.app.repository.MovieRepository;
import movie.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final TokenService tokenService;

    @Autowired
    public MovieController(MovieRepository movieRepository, TokenService tokenService) {
        this.movieRepository = movieRepository;
        this.tokenService = tokenService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieInfo>> getAllMovies(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        // Verify the token (you can customize this based on your needs)
        if (verifyToken(token)) {
            List<MovieInfo> movies = (List<MovieInfo>) movieRepository.findAll();
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    private String extractTokenFromHeaders(HttpHeaders headers) {
        // Extract the token from the Authorization header
        List<String> authorizationHeaders = headers.get("Authorization");
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String headerValue = authorizationHeaders.get(0);
            if (headerValue != null && headerValue.startsWith("Bearer ")) {
                return headerValue.substring(7);
            }
        }
        return null;
    }

    private boolean verifyToken(String token) {
        // Verify the token (you can customize this based on your needs)
        // Here, we simply check if the token is present in the cache
        return token != null && tokenService.containsValue(token);
    }
}
