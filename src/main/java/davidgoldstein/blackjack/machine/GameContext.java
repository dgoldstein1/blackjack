package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.ActionRequest;
import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.model.GameStatus;

/**
 * Context passed to the machine to validate transitions
 */
public class GameContext {
    GameState gameState;
    ActionRequest actionRequest;

    public GameContext(GameState gs) {
        this.gameState = gs;
    }

    public GameContext(GameState gs, ActionRequest ar) {
        this.gameState = gs;
        this.actionRequest = ar;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public ActionRequest getActionRequest() {
        return this.actionRequest;
    }
}
