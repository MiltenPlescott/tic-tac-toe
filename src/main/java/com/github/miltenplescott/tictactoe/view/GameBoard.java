/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Milten Plescott
 */
public class GameBoard extends JPanel {

	private List<List<GameBoardButton>> buttons;

	private GameBoard() {

	}

	public static GameBoard getInstance() {
		return GameBoardHolder.INSTANCE;
	}

	private static class GameBoardHolder {

		private static final GameBoard INSTANCE = new GameBoard();
	}
}
