/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.controller;

import com.github.miltenplescott.tictactoe.GameBoardCellNotEmptyException;
import com.github.miltenplescott.tictactoe.Main;
import com.github.miltenplescott.tictactoe.model.Model;
import com.github.miltenplescott.tictactoe.view.GameBoard;
import com.github.miltenplescott.tictactoe.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardController {

	public GameBoardController(Model model, View view) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				GameBoard gb = view.getGameBoard();
				List<Integer> buttonIndices = getButtonIndices(gb, event);
				int row = buttonIndices.get(0);
				int col = buttonIndices.get(1);
				System.out.println("" + row + "" + col);

				try {
					model.setSymbol(gb.transformIndices(row, col), model.getGameSettingsSymbol());
				}
				catch (GameBoardCellNotEmptyException ex) {
					// call view method to turn the gb.transformIndices(row, col) button red for a second
					// this will bypass the model, as this is purely a UI matter
				}
			}
		};
		view.setGameBoardButtonsListener(listener);
	}

	private List<Integer> getButtonIndices(GameBoard gb, ActionEvent event) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (gb.getButton(row, col).equals(event.getSource())) {
					return Arrays.asList(row, col);
				}
			}
		}
		return null;
	}

}
