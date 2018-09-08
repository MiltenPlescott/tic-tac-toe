/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.model;

import com.github.miltenplescott.tictactoe.GameBoardCellNotEmptyException;
import com.github.miltenplescott.tictactoe.GameBoardIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milten Plescott
 */
public class Model {

	public static final int BOARD_SIZE = 9;

	// game settings:
	private final Symbol symbol;
	private final Difficulty difficulty;

	/*
	 *  board indices:
	 *   0 1 2
	 *   3 4 5
	 *   6 7 8
	 */
	private List<Symbol> board;

	public Model(Symbol symbol, Difficulty difficulty) {
		this.symbol = symbol;
		this.difficulty = difficulty;
		reset();
	}

	void reset() {
		board = new ArrayList<>(BOARD_SIZE);
		for (int i = 0; i < BOARD_SIZE; i++) {
			board.add(Symbol.none);
		}
	}

	public void setSymbol(int boardIndex, Symbol symbol) {
		if (boardIndex < 0 || boardIndex > 8) {
			throw new GameBoardIndexOutOfBoundsException(boardIndex);
		}
		else if (board.get(boardIndex) != Symbol.none) {
			throw new GameBoardCellNotEmptyException(board.get(boardIndex));
		}
		board.set(boardIndex, symbol);
	}

	public Symbol getSymbol(int boardIndex) {
		return board.get(boardIndex);
	}

	public Symbol getGameSettingsSymbol() {
		return this.symbol;
	}

	public Difficulty getGameSettingsDifficulty() {
		return this.difficulty;
	}
}
