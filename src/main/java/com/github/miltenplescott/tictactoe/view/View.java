/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author Milten Plescott
 */
public class View {

	private final Frame frame;

	public View() {
		frame = new Frame();
		frame.addGuiComponents();
		frame.setVisible(true);
		frame.calculateDimensions();
		frame.sliderSetup();
	}

	public List<List<GameBoardButton>> getGameBoardButtons() {
		return frame.getGameBoard().getButtons();
	}

	public void setGameBoardButtonsListener(ActionListener listener) {
		frame.getGameBoard().setListener(listener);
	}

	public GameBoard getGameBoard() {
		return frame.getGameBoard();
	}

}
