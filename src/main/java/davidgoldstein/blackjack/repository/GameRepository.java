package davidgoldstein.blackjack.repository;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Game;

/**
 * Interface that all implementations of a repository must implement
 */
public interface GameRepository {
    public Game retrieveById(String gameId) throws GameNotFoundException;
    public GameState createGame(String gameId) throws GameAlreadyExistsException;
    public GameState setGameState(String gameId, GameState gs) throws GameNotFoundException;
}