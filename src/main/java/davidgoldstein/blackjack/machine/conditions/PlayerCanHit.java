package davidgoldstein.blackjack.machine.conditions;
import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PersonStatus;
import org.squirrelframework.foundation.fsm.Condition;

public class PlayerCanHit implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());
        return p.getStatus().equals(PersonStatus.HAS_BET.toString()) && p.getHand().maxPoints() <= 21;
    }

    @Override
    public String name() {
        return "player is able to add another card";
    }
}
