package com.davidgoldstein.blackjack.model;

/**
 * occurs when the deck is empty but an operation was requested 
 * @author Z008HBS
 */
public class InvalidMoveException extends Exception {
	private static final long serialVersionUID = 1L;
	InvalidMoveException(String msg) {
		super(msg);
	}

}
