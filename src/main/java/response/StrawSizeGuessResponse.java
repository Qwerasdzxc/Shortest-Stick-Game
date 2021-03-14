package response;

import java.util.UUID;

public class StrawSizeGuessResponse {

    private boolean isSuccess;
    private boolean endGame;

    public StrawSizeGuessResponse(boolean isSuccess, boolean endGame) {
        this.isSuccess = isSuccess;
        this.endGame = endGame;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public boolean isEndGame() {
        return endGame;
    }
}
