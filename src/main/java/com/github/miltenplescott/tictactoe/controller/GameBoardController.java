/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.controller;

import com.github.miltenplescott.tictactoe.Colors;
import com.github.miltenplescott.tictactoe.GameBoardCellNotEmptyException;
import com.github.miltenplescott.tictactoe.model.Model;
import com.github.miltenplescott.tictactoe.view.GameBoard;
import com.github.miltenplescott.tictactoe.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardController {

	private GameBoardController() {
		throw new AssertionError("Suppress default constructor for noninstantiability.");
	}

	public static void initGameBoardController(Model model, View view) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				GameBoard gb = view.getGameBoard();
				Integer buttonIndex = getButtonIndex(gb, event);
				try {
					model.setSymbol(buttonIndex, model.getUserSymbol());
				}
				catch (GameBoardCellNotEmptyException ex) {
					gb.buttonFlash(buttonIndex, Colors.RED, 500);
				}
				model.userMove(buttonIndex);
			}
		};
		view.setGameBoardButtonsListener(listener);
	}

	// returns index of the button that user clicked
	private static Integer getButtonIndex(GameBoard gb, ActionEvent event) {
		for (int i = 0; i < Model.BOARD_SIZE; i++) {
			if (gb.getButton(i).equals(event.getSource())) {
				return i;
			}
		}
		return null;
	}

}
