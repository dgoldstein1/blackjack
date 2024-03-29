package davidgoldstein.blackjack.machine;

import davidgoldstein.blackjack.api.HitMeRequest;
import davidgoldstein.blackjack.api.JoinGameRequest;
import davidgoldstein.blackjack.api.PlaceBetRequest;
import davidgoldstein.blackjack.api.SplitRequest;
import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import davidgoldstein.blackjack.model.*;
;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

@StateMachineParameters(stateType = GameStatus.class, eventType = Action.class, contextType = GameContext.class)
public class GameStateMachine extends AbstractUntypedStateMachine {

    protected void applyPlayerJoinGame(GameStatus from, GameStatus to, Action event, GameContext gc) {
        JoinGameRequest jgr = (JoinGameRequest) gc.getActionRequest();
        Player p = new Player(jgr.getName());
        p.setID(gc.getActionRequest().getUserId());
        p.setMoney(100);

        if (gc.getGame().getPlayers() == null) {
            gc.getGame().setPlayers(new Player[]{p});
            return;

        }

        int currentNPlayers = gc.getGame().getPlayers().length;
        Player[] newPlayers = new Player[currentNPlayers +1];
        for (int i = 0; i < currentNPlayers; i++) {
            if (gc.getGame().getPlayers()[i].getId().equals(jgr.getUserId())) {
                return;
            }
            newPlayers[i] = gc.getGame().getPlayers()[i];
        }
        newPlayers[currentNPlayers] = p;

        gc.getGame().setPlayers(newPlayers);
    }

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
        HitMeRequest hmr = (HitMeRequest) gc.getActionRequest();
        gc.getGame().hitPlayer(hmr.getUserId(), hmr.getHandNumber());
        this.fire(Action.INTERNAL_END_GAME, gc);
    }

    protected void applySplit(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        SplitRequest spr = (SplitRequest) gc.getActionRequest();
        Player p = gc.getGame().getPlayer(gc.getActionRequest().getUserId());
        p.splitHand(spr.getHandNumber());
        this.fire(Action.INTERNAL_END_GAME, gc);
    }

    /**
     * increase the value of your initial bet by up to 100 per cent.
     * In return, the player must stand after taking one more card.
     */
    protected void applyDouble(GameStatus from, GameStatus to, Action event, GameContext gc) throws DeckIsEmptyException {
        Player p = gc.game.getPlayer(gc.getActionRequest().getUserId());
        p.setBet(p.getBet() + p.getBet());
        gc.getGame().hitPlayer(p.getId(), 0);
        if (!p.getStatus().equals(PersonStatus.BUSTED.toString())) {
            p.setStatus(PersonStatus.STOOD.toString());
        }
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
