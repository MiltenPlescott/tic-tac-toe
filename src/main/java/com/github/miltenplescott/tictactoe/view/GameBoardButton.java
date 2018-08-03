/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Milten Plescott
 */
public class GameBoardButton extends JButton {

	public GameBoardButton(String buttonName) {
		super(buttonName);
		this.setFocusable(false);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
//		Dimension dim = super.getPreferredSize();
//		int side = (int) (dim.getWidth() < dim.getHeight() ? dim.getHeight() : dim.getWidth());
//		return new Dimension(side, side);
	}

}
