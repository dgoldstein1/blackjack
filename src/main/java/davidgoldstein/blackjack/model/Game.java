package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Document
public class Game implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;
    private int pot;
    private Player[] players;
    private Dealer dealer;

    public Game() {
        this.status = GameStatus.INIT.toString();
        this.pot = 0;
        this.dealer = new Dealer();
    }
    public Game(String id) {
        this();
        this.id = id;
    }
    public Game(String id, String status) {
        this(id);
        this.status = status;
    }
    public Game(String id, String status, Player[] players) {
        this(id, status);
        this.players = players;
    }
    public void setName(String name ){ this.name = name;}
    public String getName() { return this.name; }
    public String getId() {return id;}
    public String getStatus() { return status;}
    public void setStatus(String s) { this.status = s;}
    public void setPlayers(Player[] players) {this.players = players;}
    public Player[] getPlayers() { return this.players;}
    public int getPot() { return this.pot;}
    public int incrPot(int m) {
        this.pot += m;
        return this.pot;
    }
    public Dealer getDealer() { return this.dealer;}
    public Player getPlayer(UUID id) {
        for (Player p: players) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new IllegalArgumentException("player " + id + " not found in players: " + Arrays.toString(players));
    }

    /**
     * deals cards out to players
     */
    public void dealCards() throws DeckIsEmptyException {
        for (Player p : this.players) {
            p.setHand(dealer.dealHand());
        }
        dealer.setHand(dealer.dealHand());
    }

    // finds winner, distributes money accordingly
    private void assignWinner() {
        int highScore = 0;
        UUID winningPlayer = null;
        for(Player p : players) {
            if (!p.getStatus().equals(PlayerStatus.BUSTED.toString()) && p.getPointsInHand() > highScore) {
                highScore = p.getPointsInHand();
                winningPlayer = p.getId();
            }
        }
        // check dealer has not won
        if (winningPlayer != null) {
            getPlayer(winningPlayer).incrMoney(pot);
        }
        pot = 0;
    }
    /**
     * ends a game, winnings are tallied and players statuses are updated
     */
    public void end() {
        // TODO: dealer needs to hit or stand
        // give pot to winner
        assignWinner();
        // put cards in discard pile
        for (Player p: players) {
            dealer.discard(p.discardHand());
        }
        dealer.discard(dealer.discardHand());
    }

    // deal another card to player, bust if too much
    public void hitPlayer(UUID playerId) throws DeckIsEmptyException {
        Player p = getPlayer(playerId);
        p.addCardToHand(dealer.dealCard());
        if (p.getPointsInHand() > 21) {
            p.setStatus(PlayerStatus.BUSTED.toString());
        }
    }
}
