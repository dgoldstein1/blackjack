package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.api.SplitRequest;
import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.PersonStatus;
import davidgoldstein.blackjack.model.Player;
import org.squirrelframework.foundation.fsm.Condition;

public class PlayerCanSplit implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        SplitRequest sr = (SplitRequest)  gc.getActionRequest();
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());

        // make sure has not already stood or busted
        if (!p.getStatus().equals(PersonStatus.HAS_BET.toString())) {
            return false;
        }
        // cannot split if already split twice
        if (p.hasAlreadySplitTwice()) {
            return false;
        }
        // hand must exist
        if (!p.handExists(sr.getHandNumber())) {
            return false;
        }
        // must be only two cards in hand, and cardType must be the same
        return p.getAllHands()[sr.getHandNumber()].isTwoPair();
    }

    @Override
    public String name() {
        return "player can split hand";
    }
}
