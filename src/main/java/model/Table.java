package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Table {

    private Player[] players;

//    private Map<UUID, Boolean> playerGuesses;

    private AtomicInteger round = new AtomicInteger(0);

    private Integer shortStraw;

    private Integer pickedStraw;

    public Table() {
        this.players = new Player[6];
        this.setShortStraw();
    }

    public synchronized int giveSeat(Player player) {
        for (int i = 0; i < 6 ; i++) {
            if(players[i] == null) {
                players[i] = player;
                return i + 1;
            }
        }

        return -1;
    }

//    public synchronized void setPlayerGuess(UUID player, Boolean isShort) {
//        this.playerGuesses.put(player, isShort);
//    }
//
//    public synchronized boolean getPlayerGuess(UUID player) {
//        return this.playerGuesses.get(player);
//    }

    public void setShortStraw() {
        round.addAndGet(1);

        System.out.println("Round " + getRound() + " beginning!");

        shortStraw = new Random().nextInt(6 - getRound()) + 1;
    }
//    public boolean pickStraw() {
//        round.addAndGet(1);
//
//        int playerCount = getPlayerCount();
//
//        double chance = 1.0 / playerCount;
//        double randomValue = new Random().nextDouble();
//
//        return randomValue <= chance;
//    }

    public int getRound() {
        return round.get();
    }

    public Integer getShortStraw() {
        return shortStraw;
    }

    public void setPickedStraw(Integer pickedStraw) {
        this.pickedStraw = pickedStraw;
    }

    public Integer getPickedStraw() {
        return pickedStraw;
    }
}
