package davidgoldstein.blackjack.service;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.repository.GameAlreadyExistsException;
import davidgoldstein.blackjack.repository.GameNotFoundException;
import davidgoldstein.blackjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    @Autowired
    GameRepository repo;

    /**
     * Retrieve game
     * @param gameId
     * @return
     * @throws GameNotFoundException
     */
    public GameState retrieveById(String gameId) throws GameNotFoundException {
        return repo.retrieveById(gameId);
    }

    /**
     * lists all games in repo
     * @return
     */
    public List<GameState> listGames() {
        return repo.listGames();
    }

    public GameState createGame(String gameId) throws GameAlreadyExistsException {
        return repo.createGame(gameId);
    }
}
