package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Player;

public class PlayerEventRequestTest {
    /**
     * creates test request
     * @return randomly generated player event request
     */
    public static PlayerEventRequest TestPlayerEventRequest () {
        return new PlayerEventRequest(
                new Player("test player"),
                PlayerEvent.PLACE_BET,
                "GAME_ID"
        );
    }
}
