package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.exceptions.GameAlreadyExistsException;
import davidgoldstein.blackjack.exceptions.GameNotFoundException;

import java.util.List;

/**
 * Interface that all implementations of a repository must implement
 */
public interface GameRepository {
    public void deleteAll();
    public Game retrieveById(String gameId) throws GameNotFoundException;
    public Game createGame(Game gs) throws GameAlreadyExistsException;
    public Game updateExisting(Game gs) throws GameNotFoundException;
    public Game setGameState(String gameId, Game gs) throws GameNotFoundException;
    public List<Game> listGames();
}
