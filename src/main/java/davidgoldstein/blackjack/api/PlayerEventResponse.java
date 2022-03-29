package davidgoldstein.blackjack.api;

import java.io.Serializable;

/**
 * simple wrapper for success or failure message
 */
public class PlayerEventResponse implements Serializable {
    private String content;

    public PlayerEventResponse() {
    }

    public PlayerEventResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}