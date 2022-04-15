package davidgoldstein.blackjack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class GameState implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;
    private int pot;
    private Player[] players;

    public GameState() {
        this.status = GameStatus.INIT.toString();
        this.pot = 0;
    }
    public GameState(String id) {
        this();
        this.id = id;
    }
    public GameState(String id, String status) {
        this(id);
        this.status = status;
    }
    public GameState(String id, String status, Player[] players) {
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

}
