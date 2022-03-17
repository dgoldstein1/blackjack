package davidgoldstein.blackjack.api;

/**
 * simple wrapper for success or failure message
 */
public class PlayerEventResponse {
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