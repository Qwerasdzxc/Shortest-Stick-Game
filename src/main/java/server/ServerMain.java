package server;

import model.Table;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private static final int PORT = 9999;

    public static void main(String[] args) {

        Table table = new Table();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6);

        try {
            ServerSocket ss = new ServerSocket(PORT);
            ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println("Krupije je spreman");

            while (true) {
                Socket socket = ss.accept();
                executorService.submit(new ServerThread(socket, table, cyclicBarrier));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
