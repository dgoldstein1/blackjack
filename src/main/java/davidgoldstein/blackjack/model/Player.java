package davidgoldstein.blackjack.model;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Person, Serializable {
	private UUID id;
	private String name;
	private Hand[] hands;
	private int money;
	private int bet;
	private String status;
	private boolean hasSplit;
	
	/**
	 * Player of a card game
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
		this.hands = new Hand[]{new Hand()};
		this.money = 0;
		this.bet = 0;
		this.status = PersonStatus.INIT.toString();
		this.hasSplit = false;
	}

	public Player() {}


	@Override
	public Hand getPrimaryHand() {
		return this.hands[0];
	}


	// removes all cards, returns the cards that were in hands
	public Hand discardAllCards() {
		Hand toReturn = new Hand();
		for (Hand h: hands) {
			toReturn.add(h.asList());
		}
		hands = new Hand[]{new Hand()};
		return toReturn;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	public String getStatus() { return this.status;}
	public void setStatus(String status) {this.status = status;}
	public UUID getId() { return this.id;}
	public void setID(UUID id) {this.id = id;}
	public int getMoney() { return  this.money;}
	public void setMoney(int m) { this.money = m;}
	public void incrMoney(int m) {this.money += m;}
	public int decrMoney(int m) {
		this.money -= m;
		return this.money;
	}
	public void setPrimaryHand(Hand hand) {this.hands[0] = hand;}
	public void setHands(Hand[] hands) {this.hands = hands;}
	public void setBet(int m) {
		bet = m;}
	public int getBet() {return bet;}
	public boolean hasSplit() {return this.hasSplit;}
	public void setHasSplit() {this.hasSplit = true;}
	public Hand[] getAllHands() {return this.hands;}
	public boolean hasAlreadySplitTwice() {return this.hands.length == 3;}
	public boolean handExists(int handNumber) {
		return handNumber >= 0 && handNumber < this.hands.length;
	}

	@Override
	public String toString() {
		return String.format("{id : %s, name : %s}",id, name);
	}

	public void reset() {
		this.hands = new Hand[]{new Hand()};
		this.money = 0;
		this.bet = 0;
		this.status = PersonStatus.INIT.toString();
		this.hasSplit = false;
	}

	/**
	 * splits the and in two. Assumes that we have already
	 * validated that we can split this hand
	 * @param handNumber the hand to split
	 */
	public void splitHand(int handNumber) {
		Hand curr = this.hands[handNumber];
		this.hands[handNumber] = new Hand(curr.get(0));
		this.hands[handNumber+1] = new Hand(curr.get(1));
		hasSplit = true;
	}

	/**
	 * @return maximum points per hand less than 21
	 */
	public int getMaxPointsLT21FromAllHands() {
		int max = -1;
		for (Hand h : hands) {
			if (h.getMaxPointsLT21() > max) {
				max = h.getMaxPointsLT21();
			}
		}
		return max;
	}
}
