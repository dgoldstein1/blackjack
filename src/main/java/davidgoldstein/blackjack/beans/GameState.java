package davidgoldstein.blackjack.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class GameState implements Serializable {
    @Id
    private String id;

    public GameState() {}
    public GameState(String id) {
        this.id = id;
    }

    public String getId() {return id;}
    public void setId(String id) {
        this.id = id;
    }

}
