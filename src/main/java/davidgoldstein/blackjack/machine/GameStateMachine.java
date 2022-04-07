package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = GameStatus.class, eventType = Action.class, contextType = GameState.class)
public class GameStateMachine extends AbstractUntypedStateMachine {

    protected void waitingForBetToPlayerBets(GameStatus from, GameStatus to, Action action, GameState context) {
        System.out.println("Transition from '"+from+"' to '"+to+"' on event '"+ action + "' with context '"+context+"'.");
    }


}
