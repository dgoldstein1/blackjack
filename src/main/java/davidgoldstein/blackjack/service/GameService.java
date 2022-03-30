package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Action;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {
    public GameState createGame(UUID uuid) {
        return new GameState(uuid);
    }

    public GameState move(UUID uuid, ActionRequest req) {
        return new GameState(uuid);
    }
}


