package response;

public class StrawPickResponse {

    boolean isShort;

    public StrawPickResponse() {}

    public StrawPickResponse(boolean isShort) {
        this.isShort = isShort;
    }

    public void setIsShort(boolean isShort) {
        this.isShort = isShort;
    }

    public boolean isShort() {
        return isShort;
    }
}
