package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.PlaceBetRequest;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * assert that player have enought money in order to bet
 */
public class PlayerHasEnoughMoney implements Condition {
    @Override
    public boolean isSatisfied(Object context) {
        GameContext gc = (GameContext) context;
        PlaceBetRequest pbr = (PlaceBetRequest) gc.getActionRequest();
        return gc.getGameState().getPlayer(pbr.getUserId()).getMoney() >= pbr.getAmount();
    }

    @Override
    public String name() {
        return "player has enough money";
    }
}