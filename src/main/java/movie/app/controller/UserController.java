package movie.app.controller;

import movie.app.service.TokenService;
import movie.app.repository.UserRepository;
import movie.app.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated UserInfo userInfo, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Collect detailed error messages
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            // Return a BAD_REQUEST response with the error messages
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if the account or email already exists
        if (userRepository.existsByAccountOrEmail(userInfo.getAccount(), userInfo.getEmail())) {
            return ResponseEntity.badRequest().body("Account or email already exists");
        }

        // Set non-existing ID and save the user
        userInfo.generateRandomUserId(); // Reset ID to ensure a new one is generated
        userRepository.save(userInfo);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginRequest) {
        String account = loginRequest.get("account");
        String password = loginRequest.get("password");
//        System.out.println("account: " + account);
        // Check if the user with the provided account and password exists
        if (userRepository.existsByAccountAndPassword(account, password)) {
            // Generate JWT token with a 5-minute expiration time
            String token = generateSimpleToken();

            // Cache the token on the server side (you can use a cache manager, a map, etc.)
            tokenService.cacheToken(account, token);

            // Send the token in the response
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @DeleteMapping("/logoff")
    public ResponseEntity<String> logoff(@RequestHeader("Authorization") String token) {
        String account = tokenService.getAccountFromToken(token);

        // Remove the token from the cache
        if (account != null) {
            tokenService.removeTokenFromCache(account);
            return ResponseEntity.ok("Logoff successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    private String generateSimpleToken() {
        // Generate a simple token (you can customize this based on your needs)
        return UUID.randomUUID().toString();
    }


}
