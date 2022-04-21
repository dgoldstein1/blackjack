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
        gc.getGameState().getPlayer(pbr.getUserId()).decrMoney(pbr.getAmount());
        // update player status
        gc.getGameState().getPlayer(pbr.getUserId()).setStatus(PlayerStatus.HAS_BET.toString());
        // add money to pot
        gc.getGameState().incrPot(pbr.getAmount());
        // attempt to deal cards. This will be declined if not everyone has bet
        this.fire(Action.INTERNAL_FINISH_BETS, gc);
    }

    /**
     * deal out cards to players and dealer
     */
    protected void dealCards(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        gc.getGameState().dealCards();
    }

    @Override
    protected void afterTransitionCausedException(Object from, Object to, Object event, Object context) {
        Throwable e = getLastException().getTargetException();
        System.out.println("Unable to transition from " + from + " to " + to +": " + e.getMessage());
        setStatus(StateMachineStatus.ERROR);
    }


}
