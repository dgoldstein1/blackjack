package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.PersonStatus;
import davidgoldstein.blackjack.model.Player;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * There’s only one point in a game of blackjack when you have the
 * opportunity to double down – directly after your initial two cards
 * have been dealt.
 */
public class PlayerCanDouble implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());
        return p != null &&
                p.getStatus().equals(PersonStatus.HAS_BET.toString()) &&
                p.getHand().size() == 2 &&
                p.getBet() > p.getMoney();
    }

    @Override
    public String name() {
        return "player is able to double down";
    }
}
