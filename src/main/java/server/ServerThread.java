package server;

import com.google.gson.Gson;
import model.*;
import request.SeatRequest;
import request.StrawPickRequest;
import request.StrawSizeGuessRequest;
import response.StrawPickResponse;
import response.StrawSizeGuessResponse;
import response.TableSeatResponse;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;

public class ServerThread extends Thread {

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    private Gson gson;
    private Table table;
    private CyclicBarrier cyclicBarrier;

    public ServerThread(Socket socket, Table table, CyclicBarrier cyclicBarrier) {
        this.socket = socket;
        this.table = table;
        this.cyclicBarrier = cyclicBarrier;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson = new Gson();
    }

    public void run() {

        try {
            SeatRequest seatRequest = receiveSeatRequest();

            Player player = new Player(seatRequest.getId());

            TableSeatResponse tableSeatResponse = new TableSeatResponse();
            tableSeatResponse.setTableSeatResult(TableSeatResult.FAILURE);
            int seatNumber = table.giveSeat(player);

            if (seatNumber != -1) {
                tableSeatResponse.setTableSeatResult(TableSeatResult.SUCCESS);
                tableSeatResponse.setSeatNumber(seatNumber);
                player.setSeatNumber(seatNumber);
            }

            // Notify the player if he got the table seat
            sendSeatResponse(tableSeatResponse);

            cyclicBarrier.await();

            if (seatNumber != -1) {
                while (table.getRound() <= 6) {
                    cyclicBarrier.await();
                    // Our player is guessing the straw size
                    if (seatNumber != table.getRound()) {
                        out.println("Choose between short and long:");
                        StrawSizeGuessRequest strawSizeGuessRequest = receiveStrawSizeGuessRequest();

                        cyclicBarrier.await();
                        boolean success = strawSizeGuessRequest.isShort() == (table.getPickedStraw().equals(table.getShortStraw()));
                        System.out.println("Player chosen: " + strawSizeGuessRequest.isShort() + " and the result is: " + success);

                        if (success)
                            player.addPoint();

                        boolean endGame = table.getPickedStraw().equals(table.getShortStraw());

                        sendStrawGuessResponse(new StrawSizeGuessResponse(success, endGame));

                        if (endGame)
                            break;
                    }
                    // Our player is choosing the straw
                    else {
                        out.println("Choose your straw:");
                        table.setPickedStraw(receiveStrawPickRequest().getStraw());

                        cyclicBarrier.await();
                        System.out.println("Player picked: " + table.getPickedStraw() + " and the short straw is: " + table.getShortStraw());

                        sendStrawPickResponse(new StrawPickResponse(table.getPickedStraw().equals(table.getShortStraw())));

                        if (table.getPickedStraw().equals(table.getShortStraw()))
                            break;

                        table.setShortStraw();
                    }
                }
            }

            System.out.println("-------- END GAME ---------");
            System.out.println("Player points: " + player.getPoints());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SeatRequest receiveSeatRequest() throws IOException {
        return gson.fromJson(in.readLine(), SeatRequest.class);
    }

    private void sendSeatResponse(TableSeatResponse tableSeatResponse) {
        out.println(gson.toJson(tableSeatResponse));
    }

    public StrawSizeGuessRequest receiveStrawSizeGuessRequest() throws IOException {
        return gson.fromJson(in.readLine(), StrawSizeGuessRequest.class);
    }

    public StrawPickRequest receiveStrawPickRequest() throws IOException {
        return gson.fromJson(in.readLine(), StrawPickRequest.class);
    }

    private void sendStrawGuessResponse(StrawSizeGuessResponse response) {
        out.println(gson.toJson(response));
    }

    private void sendStrawPickResponse(StrawPickResponse response) {
        out.println(gson.toJson(response));
    }
}
