package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PersonStatus;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * all players have either busted or have stood
 */
public class AllPlayersFinishedMakingMoves implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        for (Player p: gc.getGame().getPlayers()) {
            PersonStatus status = PersonStatus.fromString(p.getStatus());
            // assert that each player has stood or busted
            if (!status.equals(PersonStatus.STOOD) && !status.equals(PersonStatus.BUSTED)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String name() {
        return "all players have either stood or busted";
    }
}
