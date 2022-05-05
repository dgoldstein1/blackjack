package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.api.ActionRequest;
import davidgoldstein.blackjack.model.Game;

/**
 * Context passed to the machine to validate transitions
 */
public class GameContext {
    Game game;
    ActionRequest actionRequest;

    public GameContext(Game gs) {
        this.game = gs;
    }

    public GameContext(Game gs, ActionRequest ar) {
        this.game = gs;
        this.actionRequest = ar;
    }

    public Game getGame() {
        return this.game;
    }

    public ActionRequest getActionRequest() {
        return this.actionRequest;
    }
}
