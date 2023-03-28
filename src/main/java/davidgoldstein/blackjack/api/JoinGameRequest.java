package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Action;

import java.util.UUID;

public class JoinGameRequest extends ActionRequest {
    private String name;
    private String playerId;

    public JoinGameRequest(){
        this.setAction(Action.JOIN_GAME.toString());
    }

    public JoinGameRequest(String action, UUID userId) {
        super(action, userId);
    }

    public JoinGameRequest(UUID userId, String name) {
        this(Action.JOIN_GAME.toString(), userId);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }

    public JoinGameRequest(UUID userId, String name, String playerId) {
        this(userId, name);
        this.playerId = playerId;
    }

    public String getName() { return this.name;}
    public String getPlayerId() {return this.playerId;}

}
