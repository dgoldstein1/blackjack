package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateMachineTests {

    @Test
    public void startGame() {
        UntypedStateMachine gsm = GameStateMachineBuilder.build();
        gsm.fire(Action.START_GAME);
        assertEquals(GameStatus.STARTED, gsm.getCurrentState());
    }
}
