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
    private String action;

    public ActionRequest(String action, UUID userId) {
        this.userId = userId;
        this.action = action;
    }

    public Action getAction() { return  Action.fromValue(action); }
    public void setAction(String action) { this.action = action;}
    public UUID getUserId() {return this.userId;}
    public void setUserId(UUID id) {this.userId = userId;}
}
