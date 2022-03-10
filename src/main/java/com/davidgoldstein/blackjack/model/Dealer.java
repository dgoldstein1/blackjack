package com.davidgoldstein.blackjack.model;

import java.util.ArrayList;

public class Dealer implements Person{
	ArrayList<Card> hand;
	
	public Dealer() {
		this.hand = new ArrayList<Card>();
	}
	
	@Override
	public ArrayList<Card> getHand() {
		return this.hand;
	}

	@Override
	public String getName() {
		return "Dealer";
	}
	
}
