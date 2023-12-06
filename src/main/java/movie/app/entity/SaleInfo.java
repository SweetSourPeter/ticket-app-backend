package movie.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

import java.time.LocalDateTime;

@Entity
public class SaleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
    private int showCount;
    private LocalDateTime showTime;
    private int saleCount;
    private double price;

    private String movieName;


    // Getters and setters
//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }


    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
