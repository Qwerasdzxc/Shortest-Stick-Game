package client;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter player count: ");
        int PLAYER_COUNT = scanner.nextInt();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(PLAYER_COUNT);

        for (int i = 0; i < PLAYER_COUNT; i++)
            scheduledExecutorService.schedule(new ClientThread(), ((int) (new Random().nextDouble() * 1000)), TimeUnit.MILLISECONDS);

        scheduledExecutorService.shutdown();
    }
}
