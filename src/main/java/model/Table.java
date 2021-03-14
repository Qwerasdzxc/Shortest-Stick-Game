package model;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Table {

    private final Player[] players;

    private final AtomicInteger round = new AtomicInteger(0);

    private final AtomicInteger shortStraw = new AtomicInteger();
    private final AtomicInteger pickedStraw = new AtomicInteger();

    private final CyclicBarrier cyclicBarrier;

    private final AtomicBoolean isReset = new AtomicBoolean(true);
    private final AtomicBoolean isGameOver = new AtomicBoolean(false);

    public Table(CyclicBarrier cyclicBarrier) {
        this.players = new Player[6];
        this.setShortStraw();
        this.cyclicBarrier = cyclicBarrier;
    }

    public synchronized int giveSeat(Player player) {
        for (int i = 0; i < 6; i++) {
            if (players[i] == null) {
                isReset.set(false);
                isGameOver.set(false);
                players[i] = player;
                return i + 1;
            }
        }

        return -1;
    }

    public void setShortStraw() {
        round.addAndGet(1);

        System.out.println("Round " + getRound() + " beginning!");

        shortStraw.set(new Random().nextInt(6 - getRound() + 1) + 1);
    }

    public int getRound() {
        return round.get();
    }

    public Integer getShortStraw() {
        return shortStraw.get();
    }

    public void setPickedStraw(Integer pickedStraw) {
        this.pickedStraw.set(pickedStraw);
    }

    public Integer getPickedStraw() {
        return pickedStraw.get();
    }

    public synchronized void reset() {
        if (!isReset.get()) {
            isReset.set(true);

            System.out.println("------ STARTING NEW PARTY -------");

            for (int i = 0; i < 6; i++)
                players[i] = null;

            round.set(0);
            setShortStraw();
            this.pickedStraw.set(0);
        }
    }

    public boolean getIsGameOver() {
        return isGameOver.get();
    }

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver.set(isGameOver);
    }
}
