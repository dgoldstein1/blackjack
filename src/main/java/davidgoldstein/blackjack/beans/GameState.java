package davidgoldstein.blackjack.beans;

import java.io.Serializable;
import java.util.UUID;

public class GameState implements Serializable {
    private UUID id;

    public GameState() {}
    public GameState(UUID id) {
        this.id = id;
    }

    public UUID getId() {return id;}
    public void setId(UUID id) {
        this.id = id;
    }

}
