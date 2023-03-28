package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Action;

import java.util.UUID;

public class JoinGameRequest extends ActionRequest {
    private String name;

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

    public String getName() { return this.name;}
}
