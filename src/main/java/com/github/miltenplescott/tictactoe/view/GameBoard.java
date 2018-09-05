/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Milten Plescott
 */
public class GameBoard extends JPanel {

	private List<List<GameBoardButton>> buttons = new ArrayList<>(3);

	private GameBoard() {
		//this.setLayout(new GridLayout(3, 3, 0, 0));
		this.setLayout(new GridBagLayout());

		for (int row = 0; row < 3; row++) {
			buttons.add(new ArrayList<>(3));
			for (int col = 0; col < 3; col++) {
				GameBoardButton but = new GameBoardButton("" + row + col);
				buttons.get(row).add(but);
				this.add(but, gridBagConstraintsWrapper(col, row));
			}
		}
	}

	private static GridBagConstraints gridBagConstraintsWrapper(int row, int col) {
		return new GridBagConstraints(row, col, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
	}

	public List<List<GameBoardButton>> getButtons() {
		return buttons;
	}

	public GameBoardButton getButton(int row, int col) {
		try {
			return buttons.get(row).get(col);
		}
		catch (IndexOutOfBoundsException | NullPointerException e) {
			return null;
		}
	}

	public static GameBoard getInstance() {
		return GameBoardHolder.INSTANCE;
	}

	private static class GameBoardHolder {

		private static final GameBoard INSTANCE = new GameBoard();
	}
}
