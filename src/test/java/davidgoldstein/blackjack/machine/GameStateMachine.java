package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.beans.GameState;
import davidgoldstein.blackjack.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateMachine {

    @Test
    public void initializesAsStarted() {
        GameState gs = new GameState("temp");
        UntypedStateMachine gsm = GameStateMachineBuilder.build(gs);
        assertEquals(gsm.getCurrentState(), GameStatus.STARTED);
    }
}
