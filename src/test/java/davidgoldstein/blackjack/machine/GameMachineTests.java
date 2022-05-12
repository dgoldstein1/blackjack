package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.api.ActionRequest;
import davidgoldstein.blackjack.api.HitMeRequest;
import davidgoldstein.blackjack.api.PlaceBetRequest;
import davidgoldstein.blackjack.api.SplitRequest;
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
        assertNotNull(gs.getPlayer(p.getId()).getPrimaryHand());
        assertEquals(2, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertEquals(2, gs.getDealer().getPrimaryHand().size());
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
        Assertions.assertEquals(PersonStatus.HAS_BET.toString(), gs.getPlayer(p.getId()).getStatus());
        Assertions.assertEquals(10, gs.getPlayer(p.getId()).getBet());
    }

    @Test
    public void stand() throws DeckIsEmptyException {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        p.setMoney(100);
        p.setBet(50);
        p.setStatus(PersonStatus.HAS_BET.toString());
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        gs.dealCards();
        ActionRequest ar = new ActionRequest(Action.STAND.toString(), p.getId());
        gsm.fire(Action.STAND, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        // internally fires
        Assertions.assertEquals(PersonStatus.INIT.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.ENDED, gsm.getCurrentState());
        // assert that money has changed hands, either win money or loose money here
        Assertions.assertEquals(0, gs.getPlayer(p.getId()).getBet());
        Assertions.assertNotEquals(1000, gs.getPot());
        Assertions.assertNotEquals(100, gs.getPlayer(p.getId()).getMoney());
        // hand is empty
        Assertions.assertEquals(0, gs.getPlayer(p.getId()).getPrimaryHand().size());
        Assertions.assertEquals(GameStatus.ENDED.toString(), gs.getStatus());
    }

    @Test
    public void hit() throws DeckIsEmptyException {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.TWO, 2));
        hand.add(new Card(Suit.CLUBS, CardType.THREE, 3));
        p.setPrimaryHand(hand);
        int prevPointValue = p.getPrimaryHand().maxPoints();
        p.setMoney(100);
        p.setStatus(PersonStatus.HAS_BET.toString());
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        ActionRequest ar = new HitMeRequest(Action.HIT_ME.toString(), p.getId());
        gsm.fire(Action.HIT_ME, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        Assertions.assertEquals(PersonStatus.HAS_BET.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(3, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertTrue(gs.getPlayer(p.getId()).getPrimaryHand().maxPoints() <= 21);
        assertNotEquals(prevPointValue, gs.getPlayer(p.getId()).getPrimaryHand().maxPoints());
    }

    @Test
    public void hitWithSplit() throws DeckIsEmptyException {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        hand1.add(new Card(Suit.CLUBS, CardType.TWO, 2));
        hand2.add(new Card(Suit.CLUBS, CardType.TWO, 2));
        p.setHands(new Hand[]{hand1, hand2});
        p.setMoney(100);
        p.setStatus(PersonStatus.HAS_BET.toString());
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        ActionRequest ar = new HitMeRequest(p.getId(), 1);
        gsm.fire(Action.HIT_ME, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        Assertions.assertEquals(PersonStatus.HAS_BET.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(1, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertEquals(2, gs.getPlayer(p.getId()).getAllHands()[1].size());
    }

    @Test
    public void cannotHitIfBusted() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        hand.add(new Card(Suit.SPADES, CardType.TEN, 10));
        hand.add(new Card(Suit.DIAMONDS, CardType.TEN, 10));
        p.setStatus(PersonStatus.BUSTED.toString());
        p.setPrimaryHand(hand);
        int prevPointValue = p.getPrimaryHand().maxPoints();
        p.setMoney(100);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p, new Player("david1")}
        );
        ActionRequest ar = new ActionRequest(Action.HIT_ME.toString(), p.getId());
        gsm.fire(Action.HIT_ME, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        Assertions.assertEquals(PersonStatus.BUSTED.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(3, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertEquals(prevPointValue, gs.getPlayer(p.getId()).getPrimaryHand().maxPoints());
    }

    @Test
    public void cannotHitIfStood() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        hand.add(new Card(Suit.SPADES, CardType.TEN, 10));
        p.setStatus(PersonStatus.STOOD.toString());
        p.setPrimaryHand(hand);
        int prevPointValue = p.getPrimaryHand().maxPoints();
        p.setMoney(100);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p, new Player("david1")}
        );
        ActionRequest ar = new ActionRequest(Action.HIT_ME.toString(), p.getId());
        gsm.fire(Action.HIT_ME, new GameContext(gs, ar));
        Assertions.assertNull(gsm.getLastException());
        Assertions.assertEquals(PersonStatus.STOOD.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(2, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertEquals(prevPointValue, gs.getPlayer(p.getId()).getPrimaryHand().maxPoints());
    }

    @Test
    public void cannotDoubleIfHasStood() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.TEN, 10));
        hand.add(new Card(Suit.SPADES, CardType.TEN, 10));
        p.setStatus(PersonStatus.STOOD.toString());
        p.setPrimaryHand(hand);
        int prevPointValue = p.getPrimaryHand().maxPoints();
        p.setMoney(100);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p, new Player("david1")}
        );
        ActionRequest ar = new ActionRequest(Action.DOUBLE.toString(), p.getId());
        gsm.fire(Action.DOUBLE, new GameContext(gs, ar));
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(PersonStatus.STOOD.toString(), gs.getPlayer(p.getId()).getStatus());
        assertEquals(2, gs.getPlayer(p.getId()).getPrimaryHand().size());
        assertEquals(prevPointValue, gs.getPlayer(p.getId()).getPrimaryHand().maxPoints());
    }

    @Test
    public void successfulDouble() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.NINE, 9));
        hand.add(new Card(Suit.SPADES, CardType.TEN, 10));
        p.setStatus(PersonStatus.HAS_BET.toString());
        p.setPrimaryHand(hand);
        p.setMoney(100);
        p.setBet(5);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        ActionRequest ar = new ActionRequest(Action.DOUBLE.toString(), p.getId());
        gsm.fire(Action.DOUBLE, new GameContext(gs, ar));

        assertEquals(GameStatus.ENDED, gsm.getCurrentState());
        assertEquals(0, gs.getPlayer(p.getId()).getPrimaryHand().size());
        Assertions.assertEquals(0, gs.getPlayer(p.getId()).getBet());
        Assertions.assertNotEquals(1000, gs.getPot());
        Assertions.assertNotEquals(100, gs.getPlayer(p.getId()).getMoney());
    }

    @Test
    public void playerCannotSplit() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.NINE, 9));
        hand.add(new Card(Suit.SPADES, CardType.EIGHT, 8));
        p.setStatus(PersonStatus.HAS_BET.toString());
        p.setPrimaryHand(hand);
        p.setMoney(100);
        p.setBet(5);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        ActionRequest ar = (ActionRequest) new SplitRequest(p.getId(), 0);
        gsm.fire(Action.SPLIT_PAIRS, new GameContext(gs, ar));
        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(2, gs.getPlayer(p.getId()).getPrimaryHand().size());

    }

    @Test
    public void successfulSplit() {
        UntypedStateMachine gsm = GameStateMachineFactory.build(GameStatus.WAITING_FOR_PLAYER_MOVE);
        Player p = new Player("david");
        Hand hand = new Hand();
        hand.add(new Card(Suit.CLUBS, CardType.NINE, 9));
        hand.add(new Card(Suit.SPADES, CardType.NINE, 9));
        p.setStatus(PersonStatus.HAS_BET.toString());
        p.setPrimaryHand(hand);
        p.setMoney(100);
        p.setBet(5);
        // will not transition with two players here
        Game gs = new Game(
                "test1",
                GameStatus.WAITING_FOR_PLAYER_MOVE.toString(),
                new Player[]{p}
        );
        ActionRequest ar = (ActionRequest) new SplitRequest(p.getId(), 0);
        gsm.fire(Action.SPLIT_PAIRS, new GameContext(gs, ar));

        assertEquals(GameStatus.WAITING_FOR_PLAYER_MOVE, gsm.getCurrentState());
        assertEquals(1, gs.getPlayer(p.getId()).getPrimaryHand().size());
    }
}
