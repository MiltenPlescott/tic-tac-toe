/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.model;

import com.github.miltenplescott.tictactoe.GameBoardCellNotEmptyException;
import com.github.miltenplescott.tictactoe.GameBoardIndexOutOfBoundsException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milten Plescott
 */
public class Model {

	public static final int BOARD_SIZE = 9;

	// game settings:
	private final Symbol userSymbol;
	private final Symbol aiSymbol;
	private final Difficulty difficulty;
	private final boolean userStarts;

	private final Game game;

	/*
	 *  board indices:
	 *   0 1 2
	 *   3 4 5
	 *   6 7 8
	 */
	private List<Symbol> board;

	private final PropertyChangeSupport support = new PropertyChangeSupport(this);

	public Model(Symbol userSymbol, Difficulty difficulty, boolean userStarts) {
		this.userSymbol = userSymbol;
		this.aiSymbol = (userSymbol == Symbol.O ? Symbol.X : Symbol.O);
		this.difficulty = difficulty;
		this.userStarts = userStarts;
		reset();
		Ai.init(this.difficulty, this.userSymbol, this.aiSymbol);
		this.game = new Game(this);
	}

	public void startNewGame() {
		game.startNew();
	}

	protected void reset() {
		board = new ArrayList<>(BOARD_SIZE);
		for (int i = 0; i < BOARD_SIZE; i++) {
			board.add(Symbol.none);
			support.fireIndexedPropertyChange("gameBoard", i, null, Symbol.none);
		}
	}

	public void userMove(int boardIndex) {
		game.userMove(boardIndex);
	}

	public void setSymbol(int boardIndex, Symbol symbol) {
		if (boardIndex < 0 || boardIndex > 8) {
			throw new GameBoardIndexOutOfBoundsException(boardIndex);
		}
		else if (board.get(boardIndex) != Symbol.none) {
			throw new GameBoardCellNotEmptyException(board.get(boardIndex));
		}
		support.fireIndexedPropertyChange("gameBoard", boardIndex, board.get(boardIndex), symbol);
		board.set(boardIndex, symbol);
	}

	public Symbol getSymbol(int boardIndex) {
		return board.get(boardIndex);
	}

	public Symbol getUserSymbol() {
		return this.userSymbol;
	}

	public Symbol getAiSymbol() {
		return this.aiSymbol;
	}

	public Difficulty getGameSettingsDifficulty() {
		return this.difficulty;
	}

	protected boolean getUserStarts() {
		return this.userStarts;
	}

	public GameState getCurrentGameState() {
		return this.game.gameState;
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		if (property.equals("gameBoard")) {
			support.addPropertyChangeListener(property, listener);
		}
		else if (property.equals("gameState")) {
			game.addPropertyChangeListener(property, listener);
		}
	}

}
