package movie.app.entity;

import java.time.LocalDateTime;

public class PurchaseRequest {

    private double price;
    private int ticketCount;
    private String id;
    private LocalDateTime showTime;

    // Getters and setters...
    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }
    public String getMovieId() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setMovieId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}