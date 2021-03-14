package response;

import model.TableSeatResult;

public class TableSeatResponse {

    private TableSeatResult tableSeatResult;
    private Integer seatNumber;

    public TableSeatResponse() {}

    public TableSeatResult getTableSeatResult() {
        return tableSeatResult;
    }

    public void setTableSeatResult(TableSeatResult tableSeatResult) {
        this.tableSeatResult = tableSeatResult;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}
