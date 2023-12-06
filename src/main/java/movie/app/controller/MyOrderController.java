package movie.app.controller;

import movie.app.entity.MovieInfo;
import movie.app.entity.MyOrderInfo;
import movie.app.entity.PurchaseRequest;
import movie.app.entity.SaleInfo;
import movie.app.repository.MovieRepository;
import movie.app.repository.MyOrderRepository;
import movie.app.repository.SalesRepository;
import movie.app.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class MyOrderController {

    private final MyOrderRepository myOrderRepository;
    private final TokenService tokenService;
    private final MovieRepository movieRepository;
    private final SalesRepository salesRepository;
    @Autowired
    public MyOrderController(MyOrderRepository myOrderRepository, TokenService tokenService, MovieRepository movieRepository, SalesRepository salesRepository) {
        this.myOrderRepository = myOrderRepository;
        this.tokenService = tokenService;
        this.movieRepository = movieRepository;
        this.salesRepository = salesRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<MyOrderInfo>> getUserOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        // Verify the token and get the account associated with the token
        String account = verifyTokenAndGetAccount(token);

        if (account != null) {
            // Fetch orders for the specified account
            List<MyOrderInfo> userOrders = myOrderRepository.findByAccount(account);
            return ResponseEntity.ok(userOrders);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody PurchaseRequest purchaseRequest) {

        // Verify the token and get the account associated with the token
        String account = tokenService.getAccountFromToken(token);
        Optional<MovieInfo> optionalMovieInfo = movieRepository.findAllById(purchaseRequest.getId());

        if (account != null && optionalMovieInfo.isPresent()) {
            MovieInfo currentMovieInfo = optionalMovieInfo.get();

            // Create a new order
            MyOrderInfo newOrder = new MyOrderInfo();
            newOrder.setMovieName(currentMovieInfo.getMovieName());
            newOrder.setShowTime(purchaseRequest.getShowTime());
            newOrder.setPurchaseTime(new Date());
            newOrder.setTicketCount(purchaseRequest.getTicketCount());
            newOrder.setPrice(currentMovieInfo.getTicketPrice() * purchaseRequest.getTicketCount());
            newOrder.generateRandomId();
            newOrder.setAccount(account);

            // Save the new sale
            myOrderRepository.save(newOrder);

            SaleInfo newSale = new SaleInfo();
            newSale.setShowCount(purchaseRequest.getTicketCount());
            newSale.setShowTime(purchaseRequest.getShowTime());
            newSale.setSaleCount(purchaseRequest.getTicketCount());
            newSale.setPrice(currentMovieInfo.getTicketPrice() * purchaseRequest.getTicketCount());
            newSale.setMovieName(currentMovieInfo.getMovieName());

            salesRepository.save(newSale);

            return ResponseEntity.ok("Order purchased successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
    }

    private String verifyTokenAndGetAccount(String token) {
        // Verify the token (you can customize this based on your needs)
        // Here, we simply check if the token is present in the cache
        if (token != null && tokenService.containsValue(token)) {
            // Extract the account associated with the token
            return tokenService.getAccountFromToken(token);
        }
        return null;
    }
}

