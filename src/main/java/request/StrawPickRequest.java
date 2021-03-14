package request;

import java.util.UUID;

public class StrawPickRequest {

    private UUID id;

    private Integer straw;

    public StrawPickRequest(UUID id, Integer straw) {
        this.id = id;
        this.straw = straw;
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
}
