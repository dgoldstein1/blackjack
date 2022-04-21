package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import davidgoldstein.blackjack.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

import static org.junit.Assert.*;
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
    public void dealCards() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.STARTED);
        Player p = new Player("david");
        Game gs = new Game(
                "test1",
                GameStatus.STARTED.toString(),
                new Player[]{p}
            );
        gsm.fire(Action.DEAL_CARDS, new GameContext(gs));
        Assertions.assertNull(gsm.getLastException());
        assertEquals(GameStatus.WAITING_FOR_BETS, gsm.getCurrentState());
        assertNotNull(gs.getPlayer(p.getId()).getHand());
        assertEquals(2, gs.getPlayer(p.getId()).getHand().size());
        assertEquals(2, gs.getDealer().getHand().size());
    }
    @Test
    public void needAtLeastOnePlayerToDealCards() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.STARTED);
        Game gs = new Game("test1",GameStatus.STARTED.toString());
        Assertions.assertNull(gsm.getLastException());
        assertEquals(GameStatus.STARTED, gsm.getCurrentState());
    }

    @Test
    public void placeBetToWaitingForPlayerMove() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_BETS);
        Player p = new Player("david");
        p.setMoney(100);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_BETS.toString(),
                new Player[]{p}
        );
        ActionRequest ar = new PlaceBetRequest(p.getId(), 10);
        gsm.fire(Action.PLACE_BET, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        // internally fires
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        Assertions.assertEquals(PlayerStatus.HAS_BET.toString(), gs.getPlayer(p.getId()).getStatus());
        Assertions.assertEquals(10, gs.getPot());
    }

    @Test
    public void stand() throws DeckIsEmptyException {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        p.setMoney(100);
        p.setStatus(PlayerStatus.HAS_BET.toString());
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        gs.dealCards();
        gs.incrPot(5);
        ActionRequest ar = new ActionRequest(Action.STAND.toString(), p.getId());
        gsm.fire(Action.STAND, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        // internally fires
        Assertions.assertEquals(PlayerStatus.STOOD.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.ENDED, gsm.getCurrentState());
        // assert that money has changed hands, either win money or loose money here
        Assertions.assertEquals(0, gs.getPot());
        Assertions.assertNotEquals(100, gs.getPlayer(p.getId()).getMoney());
    }
}
