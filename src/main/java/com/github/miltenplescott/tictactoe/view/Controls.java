/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.view;

import javax.swing.JPanel;

/**
 *
 * @author Milten Plescott
 */
public class Controls extends JPanel {

	private Controls() {
	}

	public static Controls getInstance() {
		return ControlsHolder.INSTANCE;
	}

	private static class ControlsHolder {

		private static final Controls INSTANCE = new Controls();
	}
}
