package davidgoldstein.blackjack.machine.conditions;

import davidgoldstein.blackjack.api.JoinGameRequest;
import davidgoldstein.blackjack.api.PlaceBetRequest;
import davidgoldstein.blackjack.machine.GameContext;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * assert that player has enough money in order to bet
 */
public class PlayerHasEnoughMoney implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        PlaceBetRequest pbr = (PlaceBetRequest) gc.getActionRequest();
        return gc.getGame().getPlayer(pbr.getUserId()).getMoney() >= pbr.getAmount();
    }

    @Override
    public String name() {
        return "player has enough money";
    }
}