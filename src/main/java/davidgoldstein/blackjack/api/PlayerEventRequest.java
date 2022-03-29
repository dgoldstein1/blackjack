package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Person;

/**
 * structure for holding on to game events
 * @author Z008HBS
 *
 */
public class PlayerEventRequest {
	public final Person initiator;
	public final PlayerEvent type;
	public final String gameID;
	
	public PlayerEventRequest(Person p, PlayerEvent et, String gameID) {
		this.initiator = p;
		this.type = et;
		this.gameID = gameID;
	}

	public String toString() {
		return String.format("initiator: %s, type : %s", this.initiator, this.type);
	}
}
