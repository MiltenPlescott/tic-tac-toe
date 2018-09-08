/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardIndexOutOfBoundsException extends IndexOutOfBoundsException {

	public GameBoardIndexOutOfBoundsException() {
		super();
	}

	public GameBoardIndexOutOfBoundsException(int index) {
		super("Game board index out of range: " + index);
	}

	public GameBoardIndexOutOfBoundsException(String str) {
		super(str);
	}

}
