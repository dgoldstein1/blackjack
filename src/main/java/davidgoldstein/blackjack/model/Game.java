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
            if (p.id.equals(id)) {
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
}
