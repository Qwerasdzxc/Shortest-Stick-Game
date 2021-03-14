package model;

import java.util.UUID;

public class Player {

    private UUID id;
    private int points;
    private int seatNumber;

    public Player(UUID id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
