package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.model.Game;
import davidgoldstein.blackjack.model.Action;
import davidgoldstein.blackjack.model.GameStatus;
import davidgoldstein.blackjack.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMachineTests {


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
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.INIT);
        assertEquals(GameStatus.INIT, gsm.getCurrentState());
    }

    /**
     * goes from started to wait for bet after DEAL_CARDS action
     */
    @Test
    public void dealCardsToPlayer() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.STARTED);
        Game gs = new Game(
                "test1",
                GameStatus.STARTED.toString(),
                new Player[]{
                        new Player("david")
                }
            );
        gsm.fire(Action.DEAL_CARDS, new GameContext(gs));
        Assertions.assertNull(gsm.getLastException());
        assertEquals(GameStatus.WAITING_FOR_BETS, gsm.getCurrentState());
    }
    @Test
    public void needAtLeastOnePlayerToDealCards() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.STARTED);
        Game gs = new Game("test1",GameStatus.STARTED.toString());
        Assertions.assertNull(gsm.getLastException());
        assertEquals(GameStatus.STARTED, gsm.getCurrentState());
    }
}
