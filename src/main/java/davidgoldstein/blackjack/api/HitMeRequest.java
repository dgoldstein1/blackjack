package davidgoldstein.blackjack.api;

import davidgoldstein.blackjack.model.Action;

import java.util.UUID;

public class HitMeRequest extends ActionRequest{
    // 0: original hand, 1: extra hand number one, 2; extra hand number two
    private int handNumber;

    public HitMeRequest(String action, UUID userId) {
        super(action, userId);
    }

    public HitMeRequest(UUID userId, int handNumber) {
        this(Action.HIT_ME.toString(), userId);
        if (handNumber > 2) {
            throw new IllegalArgumentException("a player can only split two times");
        }
    }

    public int getHandNumber() { return this.handNumber;}
}