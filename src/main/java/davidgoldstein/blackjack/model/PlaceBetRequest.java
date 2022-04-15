package davidgoldstein.blackjack.model;

import java.util.UUID;

public class PlaceBetRequest extends ActionRequest{
    int amount;

    public PlaceBetRequest(String action, UUID userId) {
        super(action, userId);
    }

    public PlaceBetRequest(UUID userId, int amount) {
        this(Action.PLACE_BET.toString(), userId);
        this.amount = amount;
    }

    public int getAmount() { return this.amount;}
}
