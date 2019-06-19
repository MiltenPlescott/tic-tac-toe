/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    MIT
 */
package com.github.miltenplescott.tictactoe.view;

import com.github.miltenplescott.tictactoe.model.Model;
import java.awt.event.ActionListener;

/**
 *
 * @author Milten Plescott
 */
public class View {

	private final Frame frame;

	public View(Model model) {
		frame = new Frame();
		frame.addGuiComponents();
		frame.setVisible(true);
		frame.calculateDimensions();
		frame.sliderSetup();
		frame.setDifficulty(model.getGameSettingsDifficulty());
		model.addPropertyChangeListener(frame.getPropertyName(), frame.getPropertyChangeListener());
		model.addPropertyChangeListener(frame.getGameBoard().getPropertyName(), frame.getGameBoard().getPropertyChangeListener());
	}

	public void setGameBoardButtonsListener(ActionListener listener) {
		frame.getGameBoard().setActionListener(listener);
	}

	public GameBoard getGameBoard() {
		return frame.getGameBoard();
	}

}
