package com.davidgoldstein.blackjack.model;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements Person{
	String id;
	String name;
	ArrayList<Card> hand;
	int money;
	
	/**
	 * Player of a card game
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
		this.hand = new ArrayList<Card>();
		this.money = 0;  
	}

	@Override
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
}
