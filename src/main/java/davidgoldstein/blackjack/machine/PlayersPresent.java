package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.GameState;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * assert that at least one player is present in game
 */
public class PlayersPresent implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameState gs = (GameState) context;
        return gs.getPlayers().length > 0;
    }

    @Override
    public String name() {
        return "At least one player is present";
    }
}
