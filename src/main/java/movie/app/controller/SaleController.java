package movie.app.controller;

import movie.app.entity.SaleInfo;
import movie.app.repository.SalesRepository;
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
@RequestMapping("/api/sales")
public class SaleController {

    private final SalesRepository salesRepository;
    private final TokenService tokenService;

    @Autowired
    public SaleController(SalesRepository salesRepository, TokenService tokenService) {
        this.salesRepository = salesRepository;
        this.tokenService = tokenService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SaleInfo>> getAllSales(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // Verify the token (you can customize this based on your needs)
        if (verifyToken(token)) {
            List<SaleInfo> sales = (List<SaleInfo>) salesRepository.findAll();
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    private boolean verifyToken(String token) {
        // Verify the token (you can customize this based on your needs)
        // Here, we simply check if the token is present in the cache
        return token != null && tokenService.containsValue(token);
    }
}
