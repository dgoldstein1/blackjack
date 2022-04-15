package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;;
import davidgoldstein.blackjack.model.PlaceBetRequest;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = GameStatus.class, eventType = Action.class, contextType = GameContext.class)
public class GameStateMachine extends AbstractUntypedStateMachine {

    /**
     * apply bet from player
     */
    protected void applyBet(GameStatus from, GameStatus to, Action event, GameContext gc) {
        PlaceBetRequest pbr = (PlaceBetRequest) gc.getActionRequest();
        gc.getGameState().incrPot(pbr.getAmount());
    }

    @Override
    protected void afterTransitionCausedException(Object from, Object to, Object event, Object context) {
        Throwable e = getLastException().getTargetException();
        System.out.println("Unable to transition from " + from + " to " + to +": " + e.getMessage());
        setStatus(StateMachineStatus.ERROR);
    }

}
