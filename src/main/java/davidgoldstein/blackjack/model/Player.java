package davidgoldstein.blackjack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Person, Serializable {
	UUID id;
	String name;
	ArrayList<Card> hand;
	int money;
	String status;
	
	/**
	 * Player of a card game
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.id = UUID.randomUUID();
		this.hand = new ArrayList<Card>();
		this.money = 0;
		this.status = PlayerStatus.PLAYING.toString();
	}

	public Player() {}

	@Override
	public ArrayList<Card> getHand() {
		return this.hand;
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
	public int decrMoney(int m) {
		this.money -= m;
		return this.money;
	}
	public void setHand(ArrayList<Card> hand) {this.hand = hand;}

	@Override
	public String toString() {
		return String.format("{id : %s, name : %s}",id, name);
	}
}
