package davidgoldstein.blackjack.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class GameState implements Serializable {
    @Id
    private String id;
    private String name;

    public GameState() {}
    public GameState(String id) {
        this.id = id;
    }

    public void setName(String name ){
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public String getId() {return id;}

}
