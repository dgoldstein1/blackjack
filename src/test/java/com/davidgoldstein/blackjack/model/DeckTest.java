package com.davidgoldstein.blackjack.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class DeckTest {

	@Test
	void nextCards() {
		Deck d = new Deck();		
		// assert that we can draw cards
		List<Card> cards = new ArrayList<Card>();
		try {
			cards = d.draw(3);			
		} catch (DeckIsEmptyException e) {
			fail("exception was thrown");
		}
		assertEquals(3, cards.size(), "draws correct number of cards");
		// assert that too many cards throws error
		cards = new ArrayList<Card>();
		try {
			cards = d.draw(d.getMaxSize() + 1);
			fail("expected error to be thrown");			
		} catch (DeckIsEmptyException e) {}
		assertEquals(0, cards.size(), "returns empty array of cards");
	}
	
	@Test
	void shuffle() throws DeckIsEmptyException {
		Deck d = new Deck();
		List<Card> firstDraw = d.draw(d.getMaxSize());
		d.reset();
		List<Card> secondDraw = d.draw(d.getMaxSize());
		assertNotEquals(firstDraw, secondDraw);
		Collections.sort(firstDraw );
		Collections.sort(secondDraw);
		assertEquals(firstDraw.toString(), secondDraw.toString());
	}
	
	@Test
	void reset() throws DeckIsEmptyException {
		Deck d = new Deck();
		d.draw(3);
		int currSize = d.getSize();
		d.reset();
		assertNotEquals(currSize,d.getSize());
	}

}
