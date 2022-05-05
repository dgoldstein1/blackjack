package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@Document
public class Game implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;
    private Player[] players;
    private Dealer dealer;

    public Game() {
        this.status = GameStatus.INIT.toString();
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
            p.setHand(dealer.dealHand(false));
        }
        dealer.setHand(dealer.dealHand(true));
    }

    // finds winner, distributes money accordingly
    private void assignWinner() {
        // TODO
    }

    // put cards in discard pile
    private void discardCards() {
        for (Player p: players) {
            dealer.discard(p.discardHand());
        }
        dealer.discard(dealer.discardHand());
    }

    /**
     * ends a game, winnings are tallied and players statuses are updated
     */
    public void end() throws DeckIsEmptyException {
        // if all players have busted, dealer wins
        if (Arrays.stream(players).allMatch(p -> p.getStatus().equals(PersonStatus.BUSTED.toString()))) {
            assignWinner();
        } else {
            // have dealer finish hand
            dealer.finishHand();
            assignWinner();
        }
    }

    // reset game for next round
    public void reset() {

    }

    // deal another card to player, bust if too much
    public void hitPlayer(UUID playerId) throws DeckIsEmptyException {
        Player p = getPlayer(playerId);
        p.getHand().add(dealer.dealCard());
        if (p.getHand().maxPoints() > 21) {
            p.setStatus(PersonStatus.BUSTED.toString());
        }
    }
}
