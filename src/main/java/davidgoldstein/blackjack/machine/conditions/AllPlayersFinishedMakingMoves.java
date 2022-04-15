package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PlayerStatus;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * all players have either busted or have stood
 */
public class AllPlayersFinishedMakingMoves implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameState gs = (GameState) context;
        if (gs == null) return false;
        for (Player p: gs.getPlayers()) {
            if (p.getStatus() != PlayerStatus.PLAYING.toString()) {
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
