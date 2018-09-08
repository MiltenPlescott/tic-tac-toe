/*
 * tic-tac-toe
 *
 * Copyright (c) 2018, Milten Plescott. All rights reserved.
 *
 * SPDX-License-Identifier:    BSD-3-Clause
 */
package com.github.miltenplescott.tictactoe.model;

/**
 *
 * @author Milten Plescott
 */
public class Game {

	private Model model;

	public Game(Model model) {
		this.model = model;
	}

	private void startNew() {
		model.reset();
	}

	// new, restart, etc
}
