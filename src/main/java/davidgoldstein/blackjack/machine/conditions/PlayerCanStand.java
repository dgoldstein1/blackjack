package davidgoldstein.blackjack.machine.conditions;
import davidgoldstein.blackjack.machine.GameContext;
import davidgoldstein.blackjack.model.Player;
import davidgoldstein.blackjack.model.PlayerStatus;
import org.squirrelframework.foundation.fsm.Condition;

public class PlayerCanStand implements Condition{
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());
        return p.getStatus().equals(PlayerStatus.HAS_BET.toString());
    }

    @Override
    public String name() {
        return "player can stand";
    }
}
