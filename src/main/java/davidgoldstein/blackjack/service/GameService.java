package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {
    public GameState createGame(String gameId) {
        return new GameState(gameId);
    }

    public GameState move(String gameId, ActionRequest req) {
        return new GameState(gameId);
    }
}


