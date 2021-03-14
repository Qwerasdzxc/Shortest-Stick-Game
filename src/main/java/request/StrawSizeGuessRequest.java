package request;

import java.util.UUID;

public class StrawSizeGuessRequest {

    private UUID id;

    private Integer seat;

    private boolean isShort;

    public StrawSizeGuessRequest(UUID id, Integer seat, boolean isShort) {
        this.id = id;
        this.seat = seat;
        this.isShort = isShort;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public void setIsShort(boolean isShort) {
        this.isShort = isShort;
    }

    public boolean isShort() {
        return isShort;
    }
}
