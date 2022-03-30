package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    Map<String,GameState> games = new HashMap<>();

    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        if (games.containsKey(gameId)) {
            throw new GameAlreadyExistsException(String.format("game %s already exists",gameId));
        }
        GameState g = new GameState(gameId);
        games.put(gameId,g);
        return g;
    }

    public GameState move(String gameId, ActionRequest req) {
        return new GameState(gameId);
    }
}


