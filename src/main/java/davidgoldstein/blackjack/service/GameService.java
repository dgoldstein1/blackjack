package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.ActionRequest;
import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    Map<String, Game> games = new HashMap<>();

    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        System.out.println("CREATING GAME " + gameId);
        System.out.println(games.toString());
        if (games.containsKey(gameId)) {
            throw new GameAlreadyExistsException(String.format("game %s already exists",gameId));
        }
        Game g = new Game(gameId);
        games.put(gameId,g);
        return g.getState();
    }

    public GameState move(String gameId, ActionRequest req) {
        return new GameState(gameId);
    }
}


