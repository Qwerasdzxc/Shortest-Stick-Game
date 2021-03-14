package request;

import java.util.UUID;

public class StrawSizeGuessRequest {

    private UUID id;

    private boolean isShort;

    public StrawSizeGuessRequest(UUID id, boolean isShort) {
        this.id = id;
        this.isShort = isShort;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIsShort(boolean isShort) {
        this.isShort = isShort;
    }

    public boolean isShort() {
        return isShort;
    }
}
