package davidgoldstein.blackjack.beans;

import davidgoldstein.blackjack.model.GameStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class GameState implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;

    public GameState() {}
    public GameState(String id) {
        this.id = id;
        this.status = GameStatus.INIT.toString();
    }
    public GameState(String id, String status) {
        this.status = status;
        this.id = id;
        this.status = GameStatus.INIT.toString();
    }
    public void setName(String name ){ this.name = name;}
    public String getName() { return this.name; }
    public String getId() {return id;}
    public String getStatus() { return status;}
    public void setStatus(String s) { this.status = s;}

}
