package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

public class GameStateMachineBuilder {

    /**
     * Creates a state machine initialized to GameStatus.INIT
     */
    public static UntypedStateMachine build() {
        return build(GameStatus.INIT);
    }

    /**
     * builds a started Game State machine
     * @param initialStatus status to be initialized with
     * @return State machine
     */
    public static UntypedStateMachine build(GameStatus initialStatus) {
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(GameStateMachine.class);

        // define transitions
        builder
            .externalTransition()
            .from(GameStatus.WAITING_FOR_BET)
            .to(GameStatus.DEALING_CARDS)
            .on(Action.PLACE_BET)
            .callMethod("waitingForBetToPlayerBets");


        builder
            .externalTransition()
            .from(GameStatus.INIT)
            .to(GameStatus.STARTED)
            .on(Action.START_GAME);

        UntypedStateMachine sm = builder.newStateMachine(initialStatus);
        sm.start();
        return sm;
    }
}
