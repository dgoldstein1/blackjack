package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;

import java.util.List;

/**
 * Interface that all implementations of a repository must implement
 */
public interface GameRepository {
    public GameState retrieveById(String gameId) throws GameNotFoundException;
    public GameState createGame(String gameId) throws GameAlreadyExistsException;
    public GameState setGameState(String gameId, GameState gs) throws GameNotFoundException;
    public List<GameState> listGames();
}
