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

/**
 *
 * @author Milten Plescott
 */
public class Game {

	private final Model model;
	protected GameState gameState;
	private final int[][] patterns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);

	public Game(Model model) {
		this.model = model;
	}

	protected void startNew() {
		model.reset();
		Ai.reset();
		this.gameState = GameState.inProgress;
		firstMove();
	}

	private void firstMove() {
		if (!model.getUserStarts()) {
			int aiMove = Ai.aiMove();
			model.setSymbol(aiMove, model.getAiSymbol());
		}
	}

	protected void userMove(int boardIndex) {
		if (isGameOver()) {  // game over check after user moves
			return;
		}
		try {
			int aiMove = Ai.aiMove(boardIndex);
			model.setSymbol(aiMove, model.getAiSymbol());
		}
		catch (IllegalArgumentException e) {
			System.err.println("Invalid move. Please try again.");
		}
		catch (GameBoardIndexOutOfBoundsException | GameBoardCellNotEmptyException ex) {
			throw new AssertionError("Stupid AI.");
		}
		if (isGameOver()) {  // game over check after AI moves
			isGameOver();
		}
	}

	private boolean isGameOver() {
		GameState newGameState = getNewGameState();
		if (newGameState != GameState.inProgress) {
			this.support.firePropertyChange("gameState", this.gameState, newGameState);
			this.startNew();
			return true;
		}
		return false;
	}

	private GameState getNewGameState() {
		if (emptySpaceCount() >= 5) {
			return GameState.inProgress;
		}
		else if (threeInRow(model.getUserSymbol())) {
			return GameState.winnerUser;
		}
		else if (threeInRow(model.getAiSymbol())) {
			return GameState.winnerAi;
		}
		else if (emptySpaceCount() == 0) {
			return GameState.tie;
		}
		return GameState.inProgress;
	}

	private int emptySpaceCount() {
		int count = 0;
		for (int i = 0; i < Model.BOARD_SIZE; i++) {
			if (model.getSymbol(i) == Symbol.none) {
				count++;
			}
		}
		return count;
	}

	private boolean threeInRow(Symbol sym) {
		for (int[] pattern : patterns) {
			Symbol a = model.getSymbol(pattern[0]);
			Symbol b = model.getSymbol(pattern[1]);
			Symbol c = model.getSymbol(pattern[2]);
			if ((a == sym) && (b == sym) && (c == sym)) {
				return true;
			}
		}
		return false;
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
		support.addPropertyChangeListener(property, listener);
	}

}
