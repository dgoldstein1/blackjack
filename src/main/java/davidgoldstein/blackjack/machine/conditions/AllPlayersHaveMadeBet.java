package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PlayerStatus;
import org.squirrelframework.foundation.fsm.Condition;

import java.util.Objects;

/**
 * assert that all players have made their bets
 */
public class AllPlayersHaveMadeBet implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gs = (GameContext) context;
        for (Player p: gs.getGame().getPlayers()) {
            if (!Objects.equals(p.getStatus(), PlayerStatus.HAS_BET.toString())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String name() {
        return "all players have made their bets";
    }
}
