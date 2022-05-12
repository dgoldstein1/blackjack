package davidgoldstein.blackjack.model;

import davidgoldstein.blackjack.exceptions.DeckIsEmptyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@Document
public class Game implements Serializable {
    @Id
    private String id;
    private String name;
    private String status;
    private Player[] players;
    private Dealer dealer;

    public Game() {
        this.status = GameStatus.INIT.toString();
        this.dealer = new Dealer();
    }
    public Game(String id) {
        this();
        this.id = id;
    }
    public Game(String id, String status) {
        this(id);
        this.status = status;
    }
    public Game(String id, String status, Player[] players) {
        this(id, status);
        this.players = players;
    }
    public void setName(String name ){ this.name = name;}
    public String getName() { return this.name; }
    public String getId() {return id;}
    public String getStatus() { return status;}
    public void setStatus(String s) { this.status = s;}
    public void setPlayers(Player[] players) {this.players = players;}
    public int getPot() {return dealer.getPot();}
    public Player[] getPlayers() { return this.players;}
    public Dealer getDealer() { return this.dealer;}
    public Player getPlayer(UUID id) {
        for (Player p: players) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new IllegalArgumentException("player " + id + " not found in players: " + Arrays.toString(players));
    }

    /**
     * deals cards out to players
     */
    public void dealCards() throws DeckIsEmptyException {
        for (Player p : this.players) {
            p.setPrimaryHand(dealer.dealHand(false));
        }
        dealer.setPrimaryHand(dealer.dealHand(true));
    }

    // put cards in discard pile
    private void discardCards() {
        for (Player p: players) {
            dealer.discard(p.discardAllCards());
        }
        dealer.discard(dealer.discardAllCards());
    }

    /**
     * ends a game, winnings are tallied and players statuses are updated
     */
    public void end() throws DeckIsEmptyException {
        // if all players have busted, dealer wins and do not deal out remaining cards
        if (Arrays.stream(players).allMatch(p -> p.getStatus().equals(PersonStatus.BUSTED.toString()))) {
            // give each player's money to dealer
            Arrays.stream(players).forEach(p -> {
                dealer.incrPot(p.getBet());
                System.out.println(p.getBet());
                p.decrMoney(p.getBet());
                System.out.println(p.getMoney());
            });
            return;
        }
        // have dealer finish hand
        dealer.finishHand();
        // give money to player if they have more points and have not busted
        Arrays.stream(players).forEach(p -> {
            // if player has busted, goes to dealer
            if (p.getStatus().equals(PersonStatus.BUSTED.toString())) {
                dealer.incrPot(p.getBet());
                p.decrMoney(p.getBet());
            } else if (dealer.getStatus().equals(PersonStatus.BUSTED.toString())) {
                // give money to player if dealer busted
                p.incrMoney(p.getBet());
                dealer.decrPot(p.getBet());
            } else if (p.getPrimaryHand().getMaxPointsLT21() > dealer.getPrimaryHand().getMaxPointsLT21()) {
                // give money to player if player has more points glt 21
                p.incrMoney(p.getBet());
                dealer.decrPot(p.getBet());
            } else {
                // dealer has a better point value
                p.decrMoney(p.getBet());
                dealer.incrPot(p.getBet());
            }
        });

    }

    // reset game for next round
    public void reset() {
        for (Player p: players) {
            p.reset();
        }
        dealer.reset();
    }

    // deal another card to player, bust if too much
    // assumes we've already validated that hand exists
    public void hitPlayer(UUID playerId, int hNumber) throws DeckIsEmptyException {
        Player p = getPlayer(playerId);
        p.getAllHands()[hNumber].add(dealer.dealCard());
        if (p.getPrimaryHand().maxPoints() > 21) {
            p.setStatus(PersonStatus.BUSTED.toString());
        }
    }
}
