package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.model.Player;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * assert that at least one player is present in game
 */
public class PlayersPresent implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player[] players = gc.getGameState().getPlayers();
        return players != null && players.length > 0;
    }

    @Override
    public String name() {
        return "At least one player is present";
    }
}
