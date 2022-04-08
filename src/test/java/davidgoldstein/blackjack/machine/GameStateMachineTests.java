package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateMachineTests {


    @Test
    public void build() {
        UntypedStateMachine gsm = GameStateMachineFactory.build();
        assertTrue(gsm.isStarted());
        assertEquals(GameStatus.INIT, gsm.getCurrentState());
    }
    @Test
    public void startGame() {
        UntypedStateMachine gsm = GameStateMachineFactory.build();
        gsm.fire(Action.START_GAME);
        assertEquals(GameStatus.STARTED, gsm.getCurrentState());
    }

    @Test
    public void ableToPassInitialState() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.DEALING_CARDS);
        assertEquals(GameStatus.DEALING_CARDS, gsm.getCurrentState());
    }
}
