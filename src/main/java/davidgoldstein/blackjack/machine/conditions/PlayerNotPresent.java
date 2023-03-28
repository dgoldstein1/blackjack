package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.api.HitMeRequest;
import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.PersonStatus;
import davidgoldstein.blackjack.model.Player;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * assert that at least one player is present in game
 */
public class PlayerNotPresent implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player[] players = gc.getGame().getPlayers();
        if (players != null) {
            for (Player player: players) {
                if (player.getId() == gc.getActionRequest().getUserId()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String name() {
        return "player is already present";
    }
}
