package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Person;
import davidgoldstein.blackjack.model.Player;

import java.io.Serializable;

/**
 * structure for holding on to game events
 * @author Z008HBS
 *
 */
public class PlayerEventRequest implements Serializable {
	public final Player initiator;
	public final PlayerEvent type;
	public final String gameID;
	
	public PlayerEventRequest(Player p, PlayerEvent et, String gameID) {
		this.initiator = p;
		this.type = et;
		this.gameID = gameID;
	}

	public String toString() {
		return String.format("initiator: %s, type : %s", this.initiator, this.type);
	}
}
