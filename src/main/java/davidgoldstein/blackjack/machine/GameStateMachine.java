package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.beans.Action;
import davidgoldstein.blackjack.model.GameStatus;;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = GameStatus.class, eventType = Action.class, contextType = Integer.class)
public class GameStateMachine extends AbstractUntypedStateMachine {

    protected void waitingForBetToPlayerBets(GameStatus from, GameStatus to, Action action, Integer context) {
        System.out.println("Transition from '"+from+"' to '"+to+"' on event '"+ action + "' with context '"+context+"'.");
    }

    protected void fromStartedToDealCards(GameStatus from, GameStatus to, Action action, Integer context) {
        System.out.println("Transition from '"+from+"' to '"+to+"' on event '"+ action + "' with context '"+context+"'.");
    }
}
