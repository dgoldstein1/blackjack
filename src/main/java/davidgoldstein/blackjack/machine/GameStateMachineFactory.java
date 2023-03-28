package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.machine.conditions.*;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineLogger;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

public class GameStateMachineFactory {

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
        builder.externalTransition().from(GameStatus.INIT).to(GameStatus.INIT).on(Action.JOIN_GAME).when(new PlayerNotPresent()).callMethod("applyPlayerJoinGame");
        builder.externalTransition().from(GameStatus.INIT).to(GameStatus.STARTED).on(Action.START_GAME);
        builder.externalTransition().from(GameStatus.STARTED).to(GameStatus.WAITING_FOR_BETS).on(Action.DEAL_CARDS).when(new PlayersPresent()).callMethod("dealCards");
        // attempts INTERNAL_FINISH_BETS on success
        builder.localTransition().from(GameStatus.WAITING_FOR_BETS).to(GameStatus.WAITING_FOR_BETS).on(Action.PLACE_BET).when(new PlayerHasEnoughMoney()).callMethod("applyBet");
        builder.externalTransition().from(GameStatus.WAITING_FOR_BETS).to(GameStatus.WAITING_FOR_PLAYER_MOVE).on(Action.INTERNAL_FINISH_BETS).when(new AllPlayersHaveMadeBet());
        // attempts INTERNAL_END_GAME on success
        builder.externalTransition().from(GameStatus.WAITING_FOR_PLAYER_MOVE).to(GameStatus.WAITING_FOR_PLAYER_MOVE).on(Action.STAND).when(new PlayerCanStand()).callMethod("applyStand");
        builder.externalTransition().from(GameStatus.WAITING_FOR_PLAYER_MOVE).to(GameStatus.WAITING_FOR_PLAYER_MOVE).on(Action.HIT_ME).when(new PlayerCanHit()).callMethod("applyHit");
        builder.externalTransition().from(GameStatus.WAITING_FOR_PLAYER_MOVE).to(GameStatus.WAITING_FOR_PLAYER_MOVE).on(Action.DOUBLE).when(new PlayerCanDouble()).callMethod("applyDouble");
        // TODO
        builder.externalTransition().from(GameStatus.WAITING_FOR_PLAYER_MOVE).to(GameStatus.WAITING_FOR_PLAYER_MOVE).on(Action.SPLIT_PAIRS).when(new PlayerCanSplit()).callMethod("applySplit");
        builder.externalTransition().from(GameStatus.WAITING_FOR_PLAYER_MOVE).to(GameStatus.ENDED).on(Action.INTERNAL_END_GAME).when(new AllPlayersFinishedMakingMoves()).callMethod("end");

        builder.externalTransition().from(GameStatus.ENDED).to(GameStatus.STARTED).on(Action.START_GAME);

        UntypedStateMachine sm = builder.newStateMachine(initialStatus);
        StateMachineLogger fsmLogger = new StateMachineLogger(sm);
        fsmLogger.startLogging();
        sm.start();

        return sm;
    }
}
