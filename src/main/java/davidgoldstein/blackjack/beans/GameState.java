package davidgoldstein.blackjack.beans;

import java.io.Serializable;
import java.util.UUID;

public class GameState implements Serializable {
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
