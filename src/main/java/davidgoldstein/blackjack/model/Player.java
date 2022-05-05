package davidgoldstein.blackjack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Person, Serializable {
	private UUID id;
	private String name;
	private Hand hand;
	private int money;
	private String status;
	
	/**
	 * Player of a card game
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
		this.hand = new Hand();
		this.money = 0;
		this.status = PersonStatus.INIT.toString();
	}

	public Player() {}


	@Override
	public Hand getHand() {
		return this.hand;
	}

	// removes cards from hand, returns the cards that were in hand
	public Hand discardHand() {
		Hand currHand = hand;
		hand = new Hand();
		return currHand;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	public String getStatus() { return this.status;}
	public void setStatus(String status) {this.status = status;}
	public UUID getId() { return this.id;}
	public int getMoney() { return  this.money;}
	public void setMoney(int m) { this.money = m;}
	public void incrMoney(int m) {this.money += m;}
	public int decrMoney(int m) {
		this.money -= m;
		return this.money;
	}
	public void setHand(Hand hand) {this.hand = hand;}

	@Override
	public String toString() {
		return String.format("{id : %s, name : %s}",id, name);
	}
}
