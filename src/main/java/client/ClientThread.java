package client;

import com.google.gson.Gson;
import request.SeatRequest;
import request.StrawPickRequest;
import request.StrawSizeGuessRequest;
import response.StrawPickResponse;
import response.StrawSizeGuessResponse;
import response.TableSeatResponse;
import model.TableSeatResult;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class ClientThread implements Runnable{

    private static final int PORT = 9999;

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    private final Gson gson;

    public ClientThread() throws IOException {
        socket = new Socket("localhost", PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        gson = new Gson();
    }

    public void run() {
        try {
            SeatRequest seatRequest = new SeatRequest();
            UUID id = UUID.randomUUID();

            System.out.println("Igrac " + id.toString() + " pokusava da pristupi igri.");

            seatRequest.setId(id);
            sendSeatRequest(seatRequest);

            TableSeatResponse tableSeatResponse = receiveSeatResponse();

            if (tableSeatResponse.getTableSeatResult() == TableSeatResult.SUCCESS) {
            System.out.println("Igrac " + tableSeatResponse.getSeatNumber() + " sa ID-em: " + id.toString() + " je uspeo da se prikljuci igri.");

                int round = 1;

                while (round <= 6) {
                    if (tableSeatResponse.getSeatNumber() != round) {
                        boolean isShort = new Random().nextBoolean();
                        sendStrawSizeGuessRequest(new StrawSizeGuessRequest(id, tableSeatResponse.getSeatNumber(), isShort));
                        StrawSizeGuessResponse response = receiveStrawSizeGuessResponse();

                        if (response.isEndGame()) {
                            exitGame();
                            return;
                        }

                    } else {
                        int strawToPick = new Random().nextInt(6 - round + 1) + 1;
                        sendStrawRequest(new StrawPickRequest(id, tableSeatResponse.getSeatNumber(), strawToPick, round));
                        boolean isShort = receiveStrawPickResponse().isShort();

                        if (isShort) {
                            exitGame();
                            return;
                        }
                    }

                    round++;
                }
            } else
                System.out.println("Igrac nije uspeo da se prikljuci igri.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        exitGame();
    }

    public void sendSeatRequest(SeatRequest seatRequest) {
        out.println(gson.toJson(seatRequest));
    }

    public TableSeatResponse receiveSeatResponse() {
        try {
            return gson.fromJson(in.readLine(), TableSeatResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendStrawSizeGuessRequest(StrawSizeGuessRequest request) {
        out.println(gson.toJson(request));
    }

    public void sendStrawRequest(StrawPickRequest request) {
        out.println(gson.toJson(request));
    }

    public StrawPickResponse receiveStrawPickResponse() {
        try {
            return gson.fromJson(in.readLine(), StrawPickResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public StrawSizeGuessResponse receiveStrawSizeGuessResponse() {
        try {
            return gson.fromJson(in.readLine(), StrawSizeGuessResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void exitGame() {
        try {
            System.out.println("Igrac napusta igru.");

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
