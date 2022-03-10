package com.davidgoldstein.blackjack.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class DeckTest {

	@Test
	void nextCards() {
		Deck d = new Deck();		
		// assert that we can draw cards
		Card[] cards = new Card[0];
		try {
			cards = d.Draw(3);			
		} catch (DeckIsEmptyException e) {
			fail("exception was thrown");
		}
		assertEquals(3, cards.length, "draws correct number of cards");
		// assert that too many cards throws error
		cards = new Card[0];
		try {
			cards = d.Draw(d.getMaxSize() + 1);
			fail("expected error to be thrown");			
		} catch (DeckIsEmptyException e) {
			
		}
		assertEquals(0, cards.length, "returns empty array of cards");
	}
	
	@Test
	void shuffle() throws DeckIsEmptyException {
		Deck d = new Deck();
		Card[] firstDraw = d.Draw(d.getMaxSize());
		d.Reset();
		Card[] secondDraw = d.Draw(d.getMaxSize());
		assertNotEquals(firstDraw, secondDraw);
	}
	
	@Test
	void reset() {
		Deck d = new Deck();
		Deck a = d;
		d.Reset();
		assertNotEquals(a,d);
	}

}
