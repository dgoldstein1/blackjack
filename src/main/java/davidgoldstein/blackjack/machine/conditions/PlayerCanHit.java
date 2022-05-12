package davidgoldstein.blackjack.machine.conditions;
import davidgoldstein.blackjack.api.HitMeRequest;
import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PersonStatus;
import org.squirrelframework.foundation.fsm.Condition;

public class PlayerCanHit implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());
        HitMeRequest hr = (HitMeRequest) gc.getActionRequest();
        if (p.hasSplit()) {
            // make sure hand exists
            if (!p.handExists(hr.getHandNumber())) return false;
            // make sure this hand hasn't already busted
            if (p.getAllHands()[hr.getHandNumber()].minPoints() > 21) return false;
        }
        return p.getStatus().equals(PersonStatus.HAS_BET.toString()) && p.getPrimaryHand().maxPoints() <= 21;
    }

    @Override
    public String name() {
        return "player is able to add another card";
    }
}
