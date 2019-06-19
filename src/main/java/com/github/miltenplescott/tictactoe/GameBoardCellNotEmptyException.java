/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe;

import com.github.miltenplescott.tictactoe.model.Symbol;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardCellNotEmptyException extends RuntimeException {

	public GameBoardCellNotEmptyException() {
		super();
	}

	public GameBoardCellNotEmptyException(Symbol symbol) {
		super("Game board cell already contains symbol: " + symbol);
	}

	public GameBoardCellNotEmptyException(String str) {
		super(str);
	}

}
