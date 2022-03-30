package davidgoldstein.blackjack.beans;

import davidgoldstein.blackjack.model.Action;

import java.io.Serializable;
import java.util.UUID;

/**
 * ActionRequest holds information pertaining to a user requesting to make
 * an action in the game
 */
public class ActionRequest implements Serializable {
    // userId of the user trying to make the request
    private UUID userId;
    // type of action being requested
    private Action action;
    public ActionRequest() {}

    public Action getAction() { return  action; }
    public void setAction(Action action) { this.action = action;}
    public UUID getUserId() {return this.userId;}
    public void setUserId(UUID id) {this.userId = userId;}
}
