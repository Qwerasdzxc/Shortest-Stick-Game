package request;

import java.util.UUID;

public class StrawPickRequest {

    private UUID id;

    private Integer seat;

    private Integer straw;

    private Integer round;

    public StrawPickRequest(UUID id, Integer seat, Integer straw, Integer round) {
        this.id = id;
        this.seat = seat;
        this.straw = straw;
        this.round = round;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getStraw() {
        return straw;
    }

    public void setStraw(Integer straw) {
        this.straw = straw;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }
}
