package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import davidgoldstein.blackjack.model.*;
;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = GameStatus.class, eventType = Action.class, contextType = GameContext.class)
public class GameStateMachine extends AbstractUntypedStateMachine {

    /**
     * apply bet from player
     */
    protected void applyBet(GameStatus from, GameStatus to, Action event, GameContext gc) {
        PlaceBetRequest pbr = (PlaceBetRequest) gc.getActionRequest();
        // take away money from player
        gc.getGame().getPlayer(pbr.getUserId()).decrMoney(pbr.getAmount());
        // update player status
        gc.getGame().getPlayer(pbr.getUserId()).setStatus(PersonStatus.HAS_BET.toString());
        gc.getGame().getPlayer(pbr.getUserId()).setBet(pbr.getAmount());
        // attempt to deal cards. This will be declined if not everyone has bet
        this.fire(Action.INTERNAL_FINISH_BETS, gc);
    }

    // player has requested to stand
    protected void applyStand(GameStatus from, GameStatus to, Action event, GameContext gc) {
        Player p = gc.game.getPlayer(gc.getActionRequest().getUserId());
        p.setStatus(PersonStatus.STOOD.toString());
        this.fire(Action.INTERNAL_END_GAME, gc);
    }

    protected void applyHit(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        gc.getGame().hitPlayer(gc.getActionRequest().getUserId());
        this.fire(Action.INTERNAL_END_GAME, gc);
    }

    /**
     * increase the value of your initial bet by up to 100 per cent.
     * In return, the player must stand after taking one more card.
     */
    protected void applyDouble(GameStatus from, GameStatus to, Action event, GameContext gc) {
        Player p = gc.game.getPlayer(gc.getActionRequest().getUserId());

        this.fire(Action.INTERNAL_END_GAME, gc);
    }

    // end game
    protected void end(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        gc.getGame().end();
        gc.getGame().reset();
        gc.getGame().setStatus(GameStatus.ENDED.toString());
    }

    /**
     * deal out cards to players and dealer
     */
    protected void dealCards(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        gc.getGame().dealCards();
    }

    @Override
    protected void afterTransitionCausedException(Object from, Object to, Object event, Object context) {
        Throwable e = getLastException().getTargetException();
        System.out.println("Unable to transition from " + from + " to " + to +": " + e.getMessage());
        setStatus(StateMachineStatus.ERROR);
    }


}
